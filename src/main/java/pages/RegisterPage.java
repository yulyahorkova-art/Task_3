package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

public class RegisterPage extends BasePage {

    // Локаторы формы регистрации
    private final By nameField = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By emailField = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath(".//input[@name='Пароль']");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By errorMessage = By.xpath(".//p[contains(@class, 'input__error')]");
    private final By loginLink = By.xpath(".//a[text()='Войти']");
    private final By registerHeader = By.xpath(".//h2[text()='Регистрация']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполнить имя: {name}")
    public void setName(String name) {
        waitForElementVisible(nameField);
        driver.findElement(nameField).clear();
        driver.findElement(nameField).sendKeys(name);
    }

    @Step("Заполнить email: {email}")
    public void setEmail(String email) {
        waitForElementVisible(emailField);
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Заполнить пароль: {password}")
    public void setPassword(String password) {
        waitForElementVisible(passwordField);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажать 'Зарегистрироваться'")
    public void clickRegister() {
        waitForElementClickable(registerButton);
        driver.findElement(registerButton).click();
    }

    @Step("Получить текст ошибки")
    public String getErrorMessage() {
        try {
            waitForElementVisible(errorMessage);
            return driver.findElement(errorMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Нажать 'Войти' в форме регистрации")
    public void clickLoginLink() {
        waitForElementClickable(loginLink);
        driver.findElement(loginLink).click();
    }

    @Step("Зарегистрировать пользователя: {email}")
    public void register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegister();
    }

    @Step("Дождаться загрузки страницы регистрации")
    public void waitForRegisterPageLoad() {
        waitForElementVisible(registerHeader);
    }
}