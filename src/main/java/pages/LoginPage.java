package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    private final By emailField = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath(".//input[@name='Пароль']");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By registerLink = By.xpath(".//a[text()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath(".//a[text()='Восстановить пароль']");
    private final By loginHeader = By.xpath(".//h2[text()='Вход']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполнить email: {email}")
    public void setEmail(String email) {
        waitForElementVisible(emailField);
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
        System.out.println("Email заполнен: " + email);
    }

    @Step("Заполнить пароль")
    public void setPassword(String password) {
        waitForElementVisible(passwordField);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        System.out.println("Пароль заполнен");
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLogin() {
        waitForElementClickable(loginButton);
        driver.findElement(loginButton).click();
        System.out.println("Кнопка 'Войти' нажата");
    }

    @Step("Перейти к регистрации")
    public void goToRegister() {
        waitForElementClickable(registerLink);
        driver.findElement(registerLink).click();
        System.out.println("Переход к регистрации");
    }

    @Step("Перейти к восстановлению пароля")
    public void goToForgotPassword() {
        waitForElementClickable(forgotPasswordLink);
        driver.findElement(forgotPasswordLink).click();
        System.out.println("Переход к восстановлению пароля");
    }

    @Step("Выполнить вход: {email}")
    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLogin();
        System.out.println("Выполнен вход для: " + email);
    }

    @Step("Проверить, что открыта страница логина")
    public boolean isLoginPageDisplayed() {
        try {
            waitForElementVisible(loginHeader);
            System.out.println("Страница логина открыта");
            return driver.findElement(loginHeader).isDisplayed();
        } catch (Exception e) {
            System.out.println("Страница логина не открыта: " + e.getMessage());
            return false;
        }
    }

    @Step("Дождаться загрузки страницы логина")
    public void waitForLoginPageLoad() {
        try {
            waitForElementVisible(loginHeader);
            System.out.println("Страница логина загружена");
        } catch (Exception e) {
            System.out.println("Страница логина не загрузилась: " + e.getMessage());
            throw e;
        }
    }
}