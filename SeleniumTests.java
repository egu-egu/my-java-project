import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.assertj.core.api.Assertions;

import java.time.Duration;

public class SeleniumTests {

    WebDriver driver;
    WebDriverWait wait;

    /**
     * 1.5. Инициализация браузера вынесена в отдельный метод.
     * Выполняется перед каждым тестом.
     */
    @BeforeEach
    void setup() {
        // Инициализация ChromeDriver
        driver = new ChromeDriver();
        // Настройка явного ожидания (10 секунд)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Тест 1.1: Базовая проверка страницы входа + проверка наличия товара на главной
     */
    @Test
    void testBaseCheck() {
        // Переход на главную страницу магазина для проверки наличия товара
        driver.get("http://localhost:8080");

        // Проверка наличия заголовка
        String title = driver.getTitle();
        Assertions.assertThat(title).isNotEmpty();

        // Проверка наличия карточки товара "товар" по указанному локатору
        WebElement productCard = driver.findElement(By.xpath("//*[@id='card-3']"));
        Assertions.assertThat(productCard).isNotNull();
    }

    /**
     * Тест 1.2: Добавление товара в корзину
     */
    @Test
    void testAddProductToCart() {
        // 1. Переход на главную страницу магазина
        driver.get("http://localhost:8080");

        // 2. Находим карточку товара "товар" по ID
        WebElement productCard = driver.findElement(By.id("card-3"));

        // 3. Кликаем по карточке товара
        productCard.click();

        // 4. Ожидаем появления конкретного товара в корзине по указанному локатору
        // Согласно запросу: //*[@id="cart-item-3"]
        WebElement cartItem = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='cart-item-3']")
        ));

        // 5. Проверка: элемент найден
        Assertions.assertThat(cartItem).isNotNull();
    }

    /**
     * Тест 1.3: Вход с неверными данными
     */
    @Test
    void testLoginWithInvalidCredentials() {
        // 1. Переход на страницу входа
        driver.get("http://localhost:8080/login");

        // 2. Находим поля ввода
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));

        // 3. Вводим неверные данные
        usernameField.sendKeys("invalid_user");
        passwordField.sendKeys("invalid_password");

        // 4. Находим кнопку входа и кликаем
        WebElement signInButton = driver.findElement(By.cssSelector("button.primary"));
        signInButton.click();

        // 5. Ожидаем появления сообщения об ошибке
        
        WebElement formContainer = driver.findElement(By.xpath("/html/body/div/form/div"));
        Assertions.assertThat(formContainer).isNotNull();

        // Ищем само сообщение об ошибке внутри или рядом
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.alert-danger")
        ));

        // 6. Проверка текста ошибки
        Assertions.assertThat(errorMessage.getText()).contains("Неверные учетные данные");
    }

    /**
     * Тест 1.4: Проверить сохранение товаров в корзине после обновления страницы.
     * Сценарий:
     * 1. Добавить товар в корзину.
     * 2. Обновить страницу (F5 / refresh).
     * 3. Проверить, что товар остался в корзине (используя XPath).
     */
    @Test
    void testCartPersistenceAfterRefresh() {
        // 1. Переход на главную страницу магазина
        driver.get("http://localhost:8080");

        // 2. Добавляем товар "товар" (card-3) в корзину
        WebElement productCard = driver.findElement(By.id("card-3"));
        productCard.click();

        // 3. Убеждаемся, что товар появился в корзине
        WebElement firstCartItem = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='cart-item-3']")
        ));
        Assertions.assertThat(firstCartItem).isNotNull();

        // 4. Обновляем страницу
        driver.navigate().refresh();

        // 5. Ждем загрузки страницы и проверяем наличие товара в корзине снова через XPath
        // Если корзина сохраняется, элемент с id="cart-item-3" должен появиться снова
        WebElement persistedCartItem = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='cart-item-3']")
        ));

        // 6. Проверка: товар все еще в корзине
        Assertions.assertThat(persistedCartItem).isNotNull();
    }

    /**
     * 1.5. Закрытие браузера вынесено в отдельный метод.
     * Выполняется после каждого теста.
     */
    @AfterEach
    void tearDown() {
        // Закрытие браузера и очистка памяти после каждого теста
        if (driver != null) {
            driver.quit();
        }
    }
}
