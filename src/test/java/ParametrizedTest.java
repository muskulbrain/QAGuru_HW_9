import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;

public class ParametrizedTest {
    @BeforeAll
    public static void beforeAllMethod() {
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void beforeEach() {
        Selenide.open("https://www.dns-shop.ru/");
        $(".city-select__label_bIu").click();
        $$(".city-bubble_IBz").get(2).click();
        sleep(3000);
    }

    @ValueSource(strings = {
            "RTX3060ti", "RTX3080"
    })
    @ParameterizedTest(name = "В поисковой выдаче должны отображаться результаты содержащие {0}")
    public void testValueSourceMethod(String testData) {
        $(".presearch__input").setValue(testData).pressEnter();
        sleep(3000);
        $(".products-list ").shouldHave(Condition.text(testData));
    }

    @CsvFileSource(resources = "/Data.csv")
    @ParameterizedTest(name = "В поисковой выдаче по запросу {0} должены отображаться результаты содержащие {1}")
    public void testCSVMethod(String testData, String expectedText) {
        $(".presearch__input").setValue(testData).pressEnter();
        sleep(3000);
        $(".products-list ").shouldHave(Condition.text(expectedText));
    }

    static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of("i7", "Процессор Intel Core i7"),
                Arguments.of("palit rtx", "Видеокарта Palit GeForce RTX")
        );
    }

    @MethodSource("dataProvider")
    @ParameterizedTest(name = "В поисковой выдаче по запросу {0} должены отображаться результаты содержащие {1}")
    public void testMethodSource(String testData, String expectedText) {
        $(".presearch__input").setValue(testData).pressEnter();
        sleep(3000);
        $(".products-list ").shouldHave(Condition.text(expectedText));
    }

}
