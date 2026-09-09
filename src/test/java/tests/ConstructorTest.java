package tests;

import config.DriverFactory;
import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.ConstructorPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты конструктора")
public class ConstructorTest {
    private WebDriver driver;
    private ConstructorPage constructorPage;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        driver = DriverFactory.getDriver(browser);
        driver.get("https://qa-stellarburgers.education-services.ru/");

        constructorPage = new ConstructorPage(driver);
        constructorPage.waitForConstructorLoad();
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    @Description("Проверка работы перехода к разделу булок")
    public void testGoToBuns() {
        constructorPage.goToBuns();

        // Небольшая задержка для отображения изменений
        try { Thread.sleep(300); } catch (InterruptedException e) {}

        assertTrue(constructorPage.isBunTabActive(), "Таб 'Булки' должен быть активным");
        assertEquals("Булки", constructorPage.getActiveTabText(),
                "Активный таб должен быть 'Булки'");
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("Проверка работы перехода к разделу соусов")
    public void testGoToSauces() {
        constructorPage.goToSauces();

        try { Thread.sleep(300); } catch (InterruptedException e) {}

        assertTrue(constructorPage.isSauceTabActive(), "Таб 'Соусы' должен быть активным");
        assertEquals("Соусы", constructorPage.getActiveTabText(),
                "Активный таб должен быть 'Соусы'");
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    @Description("Проверка работы перехода к разделу начинок")
    public void testGoToFillings() {
        constructorPage.goToFillings();

        try { Thread.sleep(300); } catch (InterruptedException e) {}

        assertTrue(constructorPage.isFillingTabActive(), "Таб 'Начинки' должен быть активным");
        assertEquals("Начинки", constructorPage.getActiveTabText(),
                "Активный таб должен быть 'Начинки'");
    }

    @Test
    @DisplayName("Переключение между всеми табами")
    @Description("Проверка последовательного переключения между всеми разделами")
    public void testSwitchBetweenAllTabs() {
        constructorPage.goToBuns();
        try { Thread.sleep(200); } catch (InterruptedException e) {}
        assertTrue(constructorPage.isBunTabActive(), "Таб 'Булки' должен быть активным");

        constructorPage.goToSauces();
        try { Thread.sleep(200); } catch (InterruptedException e) {}
        assertTrue(constructorPage.isSauceTabActive(), "Таб 'Соусы' должен быть активным");

        constructorPage.goToFillings();
        try { Thread.sleep(200); } catch (InterruptedException e) {}
        assertTrue(constructorPage.isFillingTabActive(), "Таб 'Начинки' должен быть активным");

        constructorPage.goToBuns();
        try { Thread.sleep(200); } catch (InterruptedException e) {}
        assertTrue(constructorPage.isBunTabActive(), "Таб 'Булки' должен быть активным");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}