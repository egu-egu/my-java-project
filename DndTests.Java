package my;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

public class DnDTests {

    @BeforeEach
    void setup() {
        // Открываем страницу магазина
        Selenide.open("http://localhost:8080");
    }

    @Test
    void simpleDnD() {
        // 1. Находим элемент-источник (карточку товара "toy")
        
        SelenideElement sourceItem = $("#card-1");

        // Ждем появления элемента
        sourceItem.shouldHave(visible);

        // 2. Находим элемент-цель (корзину)
       
        SelenideElement targetCart = $(".cart");

        

        targetCart.shouldHave(visible);

        // 3. Выполняем действие Drag & Drop 
        
        String script =
                "var dataTransfer = new DataTransfer();" +
                        "arguments[0].dispatchEvent(new DragEvent('dragstart', {bubbles: true, cancelable: true, dataTransfer: dataTransfer}));" +
                        "arguments[1].dispatchEvent(new DragEvent('dragover', {bubbles: true, cancelable: true, dataTransfer: dataTransfer}));" +
                        "arguments[1].dispatchEvent(new DragEvent('drop', {bubbles: true, cancelable: true, dataTransfer: dataTransfer}));";

        Selenide.executeJavaScript(script, sourceItem, targetCart);

        // 4. Проверяем результат
        
        targetCart.$("#card-1").should(exist);
        
    }
}
