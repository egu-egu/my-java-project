package my;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.CollectionCondition.*;
public class SelenideTests {

    
    private static final String STORE_URL = "http://localhost:8080";
    private static final String ADMIN_URL = "http://localhost:8080/admin";

    @BeforeEach
    void setup() {
        // Открываем страницу витрины перед каждым тестом
        open(STORE_URL);
    }

    @AfterEach
    void tearDown() {
        
        if (WebDriverRunner.getWebDriver() != null) {
            WebDriverRunner.getWebDriver().quit();
        }
    }

    /**
     * 2.1. Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
     */
    @Test
    void testAddProductViaAdminAndCheckOnStore() {
        // 1. Переходим в админку
        open(ADMIN_URL);

        // 2. Логика добавления товара (предполагаем наличие формы с полями name, price и кнопкой submit)
        
        SelenideElement productNameInput = $("#product-name");
        SelenideElement productPriceInput = $("#product-price");
        SelenideElement addProductButton = $("#add-product-btn");

        productNameInput.setValue("Тестовый Товар Selenide");
        productPriceInput.setValue("1000");
        addProductButton.click();

        // 3. Возвращаемся на витрину
        open(STORE_URL);

        // 4. Проверяем, что товар появился на странице
        // Ищем элемент по тексту названия товара
        $x("//div[contains(@class, 'product-card') and contains(., 'Тестовый Товар Selenide')]")
                .should(visible, Duration.ofSeconds(5));
    }

    /**
     * 2.2. Добавить товар в корзину и проверить, что он отображается.
     */
    @Test
    void testAddProductToCartAndCheck() {
        // 1. Находим кнопку добавления в корзину для первого товара (или любого другого)
        
        SelenideElement addToCartButton = $x("//div[contains(@class, 'product-card')]//button[contains(@class, 'cart')]");

        // 2. Кликаем по кнопке
        addToCartButton.click();

        // 3. Проверяем, что товар отобразился в корзине
       
        SelenideElement cartModal = $("#cartModal");

        // Проверяем, что модалка появилась и видна
        cartModal.should(visible, Duration.ofSeconds(5));

        // Проверяем, что внутри есть хотя бы один товар
        ElementsCollection cartItems = $$(".cart-item");
        cartItems.shouldHave(sizeGreaterThanOrEqual(1), Duration.ofSeconds(5));
    }

    /**
     * 2.3. Попытаться войти в админку с неверным логином и паролем.
     */
    @Test
    void testLoginWithInvalidCredentials() {
        // 1. Переходим на страницу входа в админку
        open(ADMIN_URL + "/login");

        // 2. Заполняем поля неверными данными
        SelenideElement loginField = $("#username");
        SelenideElement passwordField = $("#password");
        SelenideElement submitButton = $("button[type='submit']");

        loginField.setValue("wrong_user");
        passwordField.setValue("wrong_pass");
        submitButton.click();

        // 3. Проверяем сообщение об ошибке
        
        SelenideElement errorMessage = $(".alert-danger");

        errorMessage.should(visible, Duration.ofSeconds(5))
                .shouldHave(text("Неверные учетные данные"));
    }

    /**
     * 2.4. Проверить сохранение товаров в корзине после обновления страницы.
     */
    @Test
    void testCartPersistenceAfterRefresh() {
        // 1. Добавляем товар в корзину 
        SelenideElement addToCartButton = $x("//div[contains(@class, 'product-card')]//button[contains(@class, 'cart')]");
        addToCartButton.click();

        // 2. Проверяем, что товар в корзине есть сейчас
        SelenideElement cartModal = $("#cartModal");
        cartModal.should(visible, Duration.ofSeconds(5));
        ElementsCollection cartItemsBefore = $$(".cart-item");
        int itemsBefore = cartItemsBefore.size();

        // 3. Обновляем страницу
        refresh();

        // 4. Проверяем, что товар остался в корзине после обновления
        // Ждем появления модалки корзины снова
        cartModal.should(visible, Duration.ofSeconds(5));

        ElementsCollection cartItemsAfter = $$(".cart-item");
        // Проверяем, что количество товаров не изменилось (или стало >= 1, если до этого было 0)
        cartItemsAfter.shouldHave(sizeGreaterThanOrEqual(itemsBefore), Duration.ofSeconds(5));
    }
