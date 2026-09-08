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
@DisplayName("Тесты личного кабинета")
public class ProfileTest {
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

        mainPage.waitForMainPageLoad();
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    @Description("Проверка перехода по клику на 'Личный кабинет'")
    public void testGoToProfile() {
        mainPage.clickPersonalAccount();
        loginPage.waitForLoginPageLoad();
        loginPage.login(user.getEmail(), user.getPassword());

        mainPage.waitForMainPageLoad();
        mainPage.clickPersonalAccount();
        profilePage.waitForProfileLoad();

        assertTrue(profilePage.isProfileDisplayed(), "Не открылся профиль");
    }

    @Test
    @DisplayName("Переход из профиля в конструктор через кнопку 'Конструктор'")
    @Description("Проверка перехода по клику на 'Конструктор'")
    public void testGoToConstructorViaButton() {
        mainPage.clickPersonalAccount();
        loginPage.waitForLoginPageLoad();
        loginPage.login(user.getEmail(), user.getPassword());

        mainPage.waitForMainPageLoad();
        mainPage.clickPersonalAccount();
        profilePage.waitForProfileLoad();
        mainPage.clickConstructor();
        mainPage.waitForMainPageLoad();

        assertTrue(mainPage.isMainPageDisplayed(),
                "Не открылась главная страница");
    }

    @Test
    @DisplayName("Переход из профиля в конструктор через логотип")
    @Description("Проверка перехода по клику на логотип")
    public void testGoToConstructorViaLogo() {
        mainPage.clickPersonalAccount();
        loginPage.waitForLoginPageLoad();
        loginPage.login(user.getEmail(), user.getPassword());

        mainPage.waitForMainPageLoad();
        mainPage.clickPersonalAccount();
        profilePage.waitForProfileLoad();
        mainPage.clickLogo();
        mainPage.waitForMainPageLoad();

        assertTrue(mainPage.isMainPageDisplayed(),
                "Не открылась главная страница");
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