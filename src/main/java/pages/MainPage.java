package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

public class MainPage extends BasePage {

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccount = By.xpath(".//a[contains(@href, '/account')]");
    private final By personalAccountText = By.xpath(".//*[text()='Личный кабинет']");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private final By logo = By.cssSelector(".AppHeader_header__logo__2D0X2");
    private final By mainHeader = By.xpath(".//h1[text()='Соберите бургер']");
    private final By constructorTabs = By.xpath(".//span[text()='Булки'] | .//span[text()='Соусы'] | .//span[text()='Начинки']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Нажать кнопку 'Войти в аккаунт'")
    public void clickLoginButton() {
        waitForElementClickable(loginButton);
        driver.findElement(loginButton).click();
        System.out.println("Кнопка 'Войти' нажата");
    }

    @Step("Нажать 'Личный кабинет'")
    public void clickPersonalAccount() {
        try {
            waitForElementClickable(personalAccount);
            driver.findElement(personalAccount).click();
            System.out.println("'Личный кабинет' нажат через ссылку");
        } catch (Exception e) {
            waitForElementClickable(personalAccountText);
            driver.findElement(personalAccountText).click();
            System.out.println("'Личный кабинет' нажат через текст");
        }
    }

    @Step("Нажать 'Конструктор'")
    public void clickConstructor() {
        waitForElementClickable(constructorButton);
        driver.findElement(constructorButton).click();
        System.out.println("'Конструктор' нажат");
    }

    @Step("Нажать логотип Stellar Burgers")
    public void clickLogo() {
        waitForElementClickable(logo);
        driver.findElement(logo).click();
        System.out.println("Логотип нажат");
    }

    @Step("Проверить, что открыта главная страница")
    public boolean isMainPageDisplayed() {
        try {
            waitForElementVisible(mainHeader);
            return driver.findElement(mainHeader).isDisplayed();
        } catch (Exception e) {
            try {
                waitForElementVisible(constructorTabs);
                return driver.findElement(constructorTabs).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    @Step("Дождаться загрузки главной страницы")
    public void waitForMainPageLoad() {
        try {
            waitForPageLoad();
            waitForElementVisible(mainHeader);
            System.out.println("Заголовок 'Соберите бургер' виден");
        } catch (Exception e) {
            waitForElementVisible(constructorTabs);
            System.out.println("Табы конструктора найдены");
        }
    }
}