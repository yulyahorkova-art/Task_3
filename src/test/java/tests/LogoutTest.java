package tests;

import api.UserClient;
import api.UserGenerator;
import config.DriverFactory;
import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты выхода из аккаунта")
public class LogoutTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private UserGenerator user;
    private String accessToken;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        driver = DriverFactory.getDriver(browser);
        driver.get("https://qa-stellarburgers.education-services.ru/");

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        user = UserGenerator.generateUser();
        UserClient.register(user);

        // Ждём загрузки главной страницы
        mainPage.waitForMainPageLoad();
        System.out.println("Главная страница загружена");
    }

    @Test
    @DisplayName("Выход из аккаунта через кнопку 'Выйти'")
    @Description("Проверка выхода по кнопке 'Выйти' в личном кабинете")
    public void testLogout() {
        // Шаг 1: Переходим на страницу логина через личный кабинет
        System.out.println("Шаг 1: Переход в личный кабинет");
        mainPage.clickPersonalAccount();
        loginPage.waitForLoginPageLoad();
        System.out.println("Страница логина загружена");

        // Шаг 2: Выполняем вход
        System.out.println("Шаг 2: Вход в аккаунт");
        loginPage.login(user.getEmail(), user.getPassword());

        // Шаг 3: Ждём загрузки главной страницы
        mainPage.waitForMainPageLoad();
        System.out.println("Главная страница загружена после входа");

        // Шаг 4: Переходим в личный кабинет
        System.out.println("Шаг 3: Переход в личный кабинет");
        mainPage.clickPersonalAccount();
        profilePage.waitForProfileLoad();
        System.out.println("Профиль загружен");

        // Шаг 5: Выходим из аккаунта
        System.out.println("Шаг 4: Выход из аккаунта");
        profilePage.logout();

        // Шаг 6: Проверяем, что открылась страница логина
        loginPage.waitForLoginPageLoad();
        System.out.println("Страница логина загружена после выхода");
        assertTrue(loginPage.isLoginPageDisplayed(),
                "Не открылась страница логина после выхода");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Браузер закрыт");
        }
        try {
            accessToken = UserClient.getAccessToken(user);
            if (accessToken != null) {
                UserClient.deleteUser(accessToken);
                System.out.println("Пользователь удалён");
            }
        } catch (Exception e) {
            System.out.println("Не удалось удалить пользователя: " + e.getMessage());
        }
    }
}