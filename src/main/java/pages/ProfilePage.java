package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

public class ProfilePage extends BasePage {

    private final By logoutButton = By.xpath(".//button[text()='Выход']");
    private final By profileHeader = By.xpath(".//a[text()='Профиль']");
    private final By orderHistory = By.xpath(".//a[text()='История заказов']");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Нажать 'Выход'")
    public void logout() {
        try {
            // Ждём, пока кнопка станет кликабельной
            waitForElementClickable(logoutButton);
            System.out.println("Кнопка 'Выход' кликабельна");
            driver.findElement(logoutButton).click();
            System.out.println("Кнопка 'Выход' нажата");
        } catch (Exception e) {
            System.out.println("Не удалось нажать 'Выход': " + e.getMessage());
            throw e;
        }
    }

    @Step("Проверить, что открыт профиль")
    public boolean isProfileDisplayed() {
        try {
            waitForElementVisible(profileHeader);
            System.out.println("Профиль открыт");
            return driver.findElement(profileHeader).isDisplayed();
        } catch (Exception e) {
            System.out.println("Профиль не открыт: " + e.getMessage());
            return false;
        }
    }

    @Step("Дождаться загрузки профиля")
    public void waitForProfileLoad() {
        try {
            waitForElementVisible(profileHeader);
            System.out.println("Заголовок 'Профиль' виден");
            waitForElementVisible(orderHistory);
            System.out.println("'История заказов' видна");
        } catch (Exception e) {
            System.out.println("Профиль не загрузился полностью: " + e.getMessage());
            // Пробуем найти хотя бы заголовок
            waitForElementVisible(profileHeader);
        }
    }
}