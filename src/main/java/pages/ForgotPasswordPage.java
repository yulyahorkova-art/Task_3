package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

public class ForgotPasswordPage extends BasePage {

    private final By loginButton = By.xpath(".//a[text()='Войти']");
    private final By forgotHeader = By.xpath(".//h2[text()='Восстановление пароля']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Нажать 'Войти' на странице восстановления пароля")
    public void clickLoginButton() {
        waitForElementClickable(loginButton);
        driver.findElement(loginButton).click();
    }

    @Step("Проверить, что открыта страница восстановления пароля")
    public boolean isForgotPasswordPageDisplayed() {
        try {
            waitForElementVisible(forgotHeader);
            return driver.findElement(forgotHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}