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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты регистрации")
public class RegistrationTest {
    private WebDriver driver;
    private MainPage mainPage;
    private RegisterPage registerPage;
    private LoginPage loginPage;
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
        user = UserGenerator.generateUser();

        mainPage.waitForMainPageLoad();
    }

    @Test
    @DisplayName("Успешная регистрация с валидными данными")
    @Description("Проверка регистрации с валидными данными")
    public void testSuccessfulRegistration() {
        mainPage.clickLoginButton();
        loginPage.waitForLoginPageLoad();
        loginPage.goToRegister();
        registerPage.waitForRegisterPageLoad();
        registerPage.register(user.getName(), user.getEmail(), user.getPassword());

        loginPage.waitForLoginPageLoad();
        assertTrue(loginPage.isLoginPageDisplayed(),
                "Не перешли на страницу логина после регистрации");
    }

    @Test
    @DisplayName("Ошибка при коротком пароле (< 6 символов)")
    @Description("Пароль меньше 6 символов должен вызывать ошибку")
    public void testShortPasswordError() {
        user.setPassword("12345");

        mainPage.clickLoginButton();
        loginPage.waitForLoginPageLoad();
        loginPage.goToRegister();
        registerPage.waitForRegisterPageLoad();
        registerPage.setName(user.getName());
        registerPage.setEmail(user.getEmail());
        registerPage.setPassword(user.getPassword());
        registerPage.clickRegister();

        String errorText = registerPage.getErrorMessage();
        assertTrue(errorText.contains("Некорректный пароль"),
                "Не появилось сообщение об ошибке пароля. Текст: " + errorText);
    }

    @ParameterizedTest
    @DisplayName("Проверка разных вариантов пароля")
    @Description("Проверка что пароль из 6 символов проходит, а из 5 - нет")
    @CsvSource({
            "123456, true",
            "12345, false"
    })
    public void testPasswordValidation(String password, boolean shouldSucceed) {
        user.setPassword(password);

        mainPage.clickLoginButton();
        loginPage.waitForLoginPageLoad();
        loginPage.goToRegister();
        registerPage.waitForRegisterPageLoad();
        registerPage.register(user.getName(), user.getEmail(), user.getPassword());

        if (shouldSucceed) {
            loginPage.waitForLoginPageLoad();
            assertTrue(loginPage.isLoginPageDisplayed(),
                    "Регистрация должна быть успешной для пароля: " + password);
        } else {
            String errorText = registerPage.getErrorMessage();
            assertTrue(errorText.contains("Некорректный пароль"),
                    "Должна быть ошибка о некорректном пароле для: " + password);
        }
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
            // Пользователь мог не создаться
        }
    }
}