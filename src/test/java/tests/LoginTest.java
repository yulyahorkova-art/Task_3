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
import pages.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты входа в аккаунт")
public class LoginTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private ForgotPasswordPage forgotPasswordPage;
    private UserGenerator user;
    private String accessToken;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        driver = DriverFactory.getDriver(browser);
        driver.get("https://qa-stellarburgers.education-services.ru/");

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);

        user = UserGenerator.generateUser();
        UserClient.register(user);

        // Ждём загрузки главной страницы
        mainPage.waitForMainPageLoad();
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    @Description("Проверка входа через главную страницу")
    public void testLoginViaMainPage() {
        mainPage.clickLoginButton();
        loginPage.waitForLoginPageLoad();
        loginPage.login(user.getEmail(), user.getPassword());

        // Проверяем, что открылась главная страница
        mainPage.waitForMainPageLoad();
        assertTrue(mainPage.isMainPageDisplayed(),
                "Не открылась главная страница после входа");
    }

    @Test
    @DisplayName("Вход через 'Личный кабинет'")
    @Description("Проверка входа через кнопку в хедере")
    public void testLoginViaPersonalAccount() {
        mainPage.clickPersonalAccount();
        loginPage.waitForLoginPageLoad();
        loginPage.login(user.getEmail(), user.getPassword());

        mainPage.waitForMainPageLoad();
        assertTrue(mainPage.isMainPageDisplayed(),
                "Не открылась главная страница после входа");
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    @Description("Проверка входа по ссылке в форме регистрации")
    public void testLoginViaRegisterForm() {
        mainPage.clickLoginButton();
        loginPage.waitForLoginPageLoad();
        loginPage.goToRegister();
        registerPage.waitForRegisterPageLoad();
        registerPage.clickLoginLink();
        loginPage.waitForLoginPageLoad();
        loginPage.login(user.getEmail(), user.getPassword());

        mainPage.waitForMainPageLoad();
        assertTrue(mainPage.isMainPageDisplayed(),
                "Не открылась главная страница после входа");
    }

    @Test
    @DisplayName("Вход через форму восстановления пароля")
    @Description("Проверка входа по ссылке на странице восстановления")
    public void testLoginViaForgotPasswordForm() {
        mainPage.clickLoginButton();
        loginPage.waitForLoginPageLoad();
        loginPage.goToForgotPassword();
        assertTrue(forgotPasswordPage.isForgotPasswordPageDisplayed(),
                "Не открылась страница восстановления пароля");
        forgotPasswordPage.clickLoginButton();
        loginPage.waitForLoginPageLoad();
        loginPage.login(user.getEmail(), user.getPassword());

        mainPage.waitForMainPageLoad();
        assertTrue(mainPage.isMainPageDisplayed(),
                "Не открылась главная страница после входа");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        try {
            accessToken = UserClient.getAccessToken(user);
            if (accessToken != null) {
                UserClient.deleteUser(accessToken);
            }
        } catch (Exception e) {
            // Игнорируем
        }
    }
}