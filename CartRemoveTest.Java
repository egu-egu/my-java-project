package my; 

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

public class CartRemoveTest {

    @BeforeEach
    void setup() {
        // Открываем главную страницу магазина, где есть товары
        Selenide.open("http://localhost:8080");
    }

    @Test
    void removeItemFromCart() {
        // 1. Добавляем товар в корзину
      
        SelenideElement addToCartButton = $x("//button[@data-action='add-to-cart']");

        // Проверяем, что кнопка существует и кликабельна 
        addToCartButton.shouldBe(visible);

        // Кликаем по кнопке добавления
        addToCartButton.click();

        // 2. Переходим в корзину
        
        SelenideElement cartButton = $x("//button[contains(@class, 'cart') or contains(text(), 'Корзина') or contains(@aria-label, 'Корзина')]");
        if (cartButton.exists()) {
            cartButton.click();
        } else {
            
            Selenide.$("#cart-items").shouldBe(visible);
        }

        // 3. Находим товар в корзине для удаления
        
        // Находим первую карточку товара в корзине
      
        SelenideElement cartItem = $$("#cart-items .product-card").first();

        // Проверяем, что товар присутствует
        cartItem.shouldBe(visible);

        // Запоминаем название товара для проверки отсутствия после удаления
        String productName = cartItem.$("h4").text(); 

        // 4. Находим и кликаем кнопку удаления
        // Кнопка удаления находится внутри карточки товара (.product-card)
       

        SelenideElement removeButton = cartItem.$("[data-action='remove']");

        // Если кнопка не найдена по data-action, пробуем другие варианты
        if (!removeButton.exists()) {
            // Вариант: кнопка с классом btn-remove или similar
            removeButton = cartItem.$(".btn-remove");
        }
        if (!removeButton.exists()) {
            
            // Ищем кнопку внутри карточки, которая содержит крестик или текст "Удалить"
            removeButton = cartItem.$x(".//button[contains(@class, 'remove') or contains(@class, 'delete') or contains(@aria-label, 'Удалить')]");
        }

        
        if (!removeButton.exists()) {
            
            removeButton = cartItem.$("button:last-of-type");
        }

        // Кликаем по кнопке удаления
        removeButton.click();

        // 5. Проверяем, что товар удален
        

        
        SelenideElement removedItemCheck = $x("//div[@id='cart-items']//h4[text()='" + productName + "']");
        removedItemCheck.shouldNot(exist);

        
    }
}
