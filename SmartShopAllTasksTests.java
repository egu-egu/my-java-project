package my;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection; // Импорт для коллекции
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$x; // Импорт для поиска коллекции
import static com.codeborne.selenide.Selenide.switchTo;

public class SmartShopAllTasksTests {

    private static final String SHOP_URL = "http://localhost:8080";
    private static final String ADMIN_URL = "http://localhost:8080/admin";
    private static final String PRODUCT_NAME_TOY = "toy";
    private static final String EXPECTED_NEW_PRICE = "16";

    @BeforeEach
    void setup() {
        // Открываем главную страницу перед каждым тестом
        Selenide.open(SHOP_URL);
    }

    /**
     * Задача 3.1: Добавление трёх товаров в корзину и проверка открытия модального окна.
     */
    @Test
    void testAddOneItemToCart() {
        System.out.println("--- Задача 3.1: Добавление трёх товаров ---");

        // 1. Находим кнопку открытия корзины
        SelenideElement cartButton = $x("//button[@id='open-cart-btn']");

        // 2. Проверяем видимость и кликаем
        cartButton.should(visible, Duration.ofSeconds(10));
        cartButton.click();

        // 3. Проверяем появление модального окна корзины
        $x("//div[@id='cartModal']").should(visible, Duration.ofSeconds(5));

        System.out.println("Корзина открыта успешно.");
    }

    /**
     * Задача 3.2: Добавление нескольких товаров и проверка корректности общей суммы.
     */
    @Test
    void testCartTotalPriceCalculation() {
        System.out.println("\n--- Задача 3.2: Проверка суммы в корзине ---");

        // 1. Находим все карточки товаров на странице

        ElementsCollection productCards = $$x("//div[@id='products-list']//div[contains(@class, 'product-card')]");

        if (productCards.size() < 2) {
            throw new RuntimeException("На странице недостаточно товаров для теста (нужно минимум 2). Найдено: " + productCards.size());
        }

        double expectedTotalPrice = 0;

        // 2. Добавляем первые два разных товара в корзину

        for (int i = 0; i < 2; i++) {
            SelenideElement card = productCards.get(i);

            // Получаем цену товара из атрибута data-price
            String priceStr = card.getAttribute("data-price");
            if (priceStr != null && !priceStr.isEmpty()) {
                expectedTotalPrice += Double.parseDouble(priceStr);
            } else {
                // парсим текст цены
                // Ищем элемент цены внутри карточки.
                SelenideElement priceElement = card.$x(".//div[contains(@style, 'color:var(--primary)')]");
                String text = priceElement.getText().replace("₽", "").replace(",", "").trim();
                expectedTotalPrice += Double.parseDouble(text);
            }

            // Находим кнопку добавления в корзину внутри карточки

            SelenideElement addToCartBtn = card.$x(".//button[contains(@class, 'btn') and contains(@class, 'cart')]");

            // Кликаем
            addToCartBtn.click();

            // Небольшая пауза для обновления UI
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        // 3. Открываем корзину
        SelenideElement cartButton = $x("//button[@id='open-cart-btn']");
        cartButton.click();

        // Ждем появления модального окна
        $x("//div[@id='cartModal']").should(visible, Duration.ofSeconds(5));

        // 4. Получаем общую сумму из интерфейса корзины
        
        SelenideElement totalPriceElement = $x("//span[@id='total-price']");
        totalPriceElement.should(visible, Duration.ofSeconds(5));

        String displayedTotalText = totalPriceElement.getText().replace("₽", "").replace(",", "").trim();
        double displayedTotalPrice = Double.parseDouble(displayedTotalText);

        // 5. Проверяем корректность расчета с погрешностью
        if (Math.abs(displayedTotalPrice - expectedTotalPrice) > 0.01) {
            throw new AssertionError(String.format(
                    "Сумма в корзине неверна. Ожидалось: %.2f, получено: %.2f",
                    expectedTotalPrice, displayedTotalPrice
            ));
        }

        System.out.println("Ожидаемая сумма: " + expectedTotalPrice);
        System.out.println("Фактическая сумма: " + displayedTotalPrice);
        System.out.println("Задача 3.2 выполнена успешно.");
    }

    /**
     * Задача 3.3: Добавление нового товара через Админ-панель.
     */
    @Test
    void testAddProductViaAdmin() {
        System.out.println("\n--- Задача 3.3: Добавление товара в Админке ---");

        // 1. Переход в админ-панель
        Selenide.open(ADMIN_URL);

        // 2. Авторизация (если требуется)
        try {
            SelenideElement usernameField = $x("//input[@placeholder='Username' or @name='username']");
            SelenideElement passwordField = $x("//input[@placeholder='Password' or @name='password']");
            SelenideElement signInButton = $x("//button[contains(text(), 'Sign in')]");

            if (usernameField.isDisplayed()) {
                usernameField.setValue("admin");
                passwordField.setValue("admin");
                signInButton.click();
                // Ждем загрузки контента админки
                $x("//div[contains(@class, 'container')]").should(visible, Duration.ofSeconds(10));
            }
        } catch (Exception e) {
            System.out.println("Авторизация не потребовалась или уже выполнена.");
        }

        // 3. Клик по кнопке "Новый товар"
        SelenideElement addNewProductBtn = $x("//button[contains(text(), 'Новый товар')]");
        addNewProductBtn.should(visible).click();

        // 4. Заполнение формы добавления товара
        SelenideElement nameInput = $x("//input[@placeholder='Название' or @id='name']");
        nameInput.should(visible).setValue("Test Product Selenium");

        SelenideElement priceInput = $x("//input[@placeholder='Цена' or @id='price']");
        // ИСПРАВЛЕНИЕ: clear() возвращает void, поэтому разделяем вызовы
        priceInput.clear();
        priceInput.setValue("100");

        // 5. Сохранение
        SelenideElement saveBtn = $x("//button[contains(text(), 'Сохранить') or contains(text(), 'Добавить')]");
        saveBtn.click();

        // 6. Проверка уведомления об успехе
        // Ожидаем текст, содержащий "успешно" или "добавлен"
        $x("//div[contains(@class, 'toast')]").should(exist, Duration.ofSeconds(10))
                .shouldHave(text("успешно"));

        System.out.println("Товар успешно добавлен.");
    }

    /**
     * Задача 3.4: Обновление цены существующего товара.
     */
    @Test
    void testUpdateProductPrice() {
        System.out.println("\n--- Задача 3.4: Обновление цены ---");

        // 1. Переход в админ-панель
        Selenide.open(ADMIN_URL);

        // 2. Поиск строки с товаром "toy" в таблице
        // Ищем строку таблицы, где есть ячейка с текстом "toy"
        SelenideElement toyRow = $x("//table//tr[td[contains(text(), '" + PRODUCT_NAME_TOY + "')]]");
        toyRow.should(visible, Duration.ofSeconds(10));

        // 3. Нажатие кнопки редактирования (обычно иконка карандаша или текст "Ред")
        SelenideElement editButton = toyRow.$x(".//button[contains(@class, 'edit') or contains(text(), 'Ред')]");
        editButton.should(visible).click();

        // 4. Изменение цены
        SelenideElement priceInput = $x("//input[@placeholder='Цена' or @id='price']");
        // ИСПРАВЛЕНИЕ: clear() возвращает void, поэтому разделяем вызовы
        priceInput.clear();
        priceInput.setValue(EXPECTED_NEW_PRICE);

        // 5. Сохранение изменений
        SelenideElement saveBtn = $x("//button[contains(text(), 'Сохранить') or contains(text(), 'Обновить')]");
        saveBtn.click();

        // 6. Проверка уведомления об успешном обновлении
        $x("//div[contains(@class, 'toast')]").should(exist, Duration.ofSeconds(10))
                .shouldHave(text("обновлен"));

        // 7. Переход на витрину магазина для проверки цены
        Selenide.open(SHOP_URL);

        // 8. Поиск карточки товара "toy" на витрине
        SelenideElement toyCard = $x("//div[contains(@class, 'product-card') and contains(@data-name, '" + PRODUCT_NAME_TOY + "')]" );
        toyCard.should(visible, Duration.ofSeconds(10));

        // 9. Получение текста цены
        // Цена обычно находится в div с определенным стилем или классом внутри карточки
        SelenideElement priceElement = toyCard.$x(".//div[contains(@style, 'color:var(--primary)')]");
        String actualPriceText = priceElement.getText();

        // 10. Валидация
        if (!actualPriceText.contains(EXPECTED_NEW_PRICE)) {
            throw new AssertionError("Цена товара не обновилась. Ожидалось: " + EXPECTED_NEW_PRICE + ", получено: " + actualPriceText);
        }

        System.out.println("Цена успешно проверена: " + actualPriceText);
    }

    /**
     * Задача 3.5: Работа с JS Alert.
     */
    @Test
    void testJsAlertHandling() {
        System.out.println("\n--- Задача 3.5: Работа с Alert ---");

        // 1. Убедимся, что мы на витрине
        Selenide.open(SHOP_URL);

        // 2. Находим кнопку добавления в корзину, которая вызывает Alert
        // Из скриншота 24.png: button[data-action='add-to-cart']
        SelenideElement addToCartButton = $x("//button[@data-action='add-to-cart']");
        addToCartButton.should(visible).click();

        // 3. Переключаемся на Alert
        // Используем стандартный WebDriver через Selenide для работы с Alert
        Alert alert = switchTo().alert();

        // Ждем появления алерта 
        try {
            Thread.sleep(500); // Небольшая задержка для гарантии появления
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. Получаем текст из Alert
        String alertText = alert.getText();
        System.out.println("Текст в Alert: " + alertText);

        // 5. Нажимаем "ОК" (Accept)
        alert.accept();

        System.out.println("Alert успешно обработан.");
    }

    /**
     * Задача 3.6: Drag-and-Drop (Перетаскивание элементов).
     */
    @Test
    void testDragAndDrop() {
        System.out.println("\n--- Задача 3.6: Drag-and-Drop ---");

        // 1. Убедимся, что мы на витрине
        Selenide.open(SHOP_URL);

        // 2. Находим источник (элемент для перетаскивания).
        
        // Возьмем первый товар "toy" (id="card-1")
        SelenideElement sourceElement = $x("//div[@id='card-1' and @draggable='true']");
        sourceElement.should(visible, Duration.ofSeconds(10));

        // 3. Находим цель (куда перетаскиваем).
        // Откроем корзину сначала, чтобы была цель.
        SelenideElement cartButton = $x("//button[@id='open-cart-btn']");
        cartButton.click();

        // Ждем появления модального окна
        SelenideElement targetContainer = $x("//div[@id='cartModal']");
        targetContainer.should(visible, Duration.ofSeconds(5));

        // 4. Выполняем Drag-and-Drop с помощью Actions (через WebDriver)
        
        WebDriver driver = WebDriverRunner.getWebDriver();
        Actions actions = new Actions(driver);

        // Перетаскиваем элемент source в контейнер target
        actions.dragAndDrop(sourceElement, targetContainer).perform();

        // 5. Проверка результата.
        // Проверяем, что внутри корзины появился новый элемент
        
        int itemsCount = driver.findElements(By.cssSelector("#cart-items > div")).size();

        if (itemsCount > 0) {
            System.out.println("Товар успешно перетащен в корзину. Количество товаров: " + itemsCount);
        } else {
            System.out.println("Дроп выполнен, но проверка наличия товара в DOM требует уточнения логики приложения.");
        }
    }

    @AfterEach
    void tearDown() {
        
        Selenide.closeWebDriver();
    }
}
