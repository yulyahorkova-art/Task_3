package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

public class ConstructorPage extends BasePage {

    // Локаторы табов
    private final By bunTab = By.xpath(".//span[text()='Булки']/parent::div");
    private final By sauceTab = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingTab = By.xpath(".//span[text()='Начинки']/parent::div");

    // Локаторы для проверки активного таба
    private final By bunTabActive = By.xpath(".//span[text()='Булки']/parent::div[contains(@class, 'tab_tab_type_current')]");
    private final By sauceTabActive = By.xpath(".//span[text()='Соусы']/parent::div[contains(@class, 'tab_tab_type_current')]");
    private final By fillingTabActive = By.xpath(".//span[text()='Начинки']/parent::div[contains(@class, 'tab_tab_type_current')]");

    // Локатор активного таба
    private final By activeTab = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]");
    private final By activeTabText = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]//span");

    // Локаторы разделов
    private final By bunSection = By.xpath(".//h2[text()='Булки']");
    private final By sauceSection = By.xpath(".//h2[text()='Соусы']");
    private final By fillingSection = By.xpath(".//h2[text()='Начинки']");

    // Локатор для проверки загрузки конструктора
    private final By anyTab = By.xpath(".//span[text()='Булки'] | .//span[text()='Соусы'] | .//span[text()='Начинки']");

    public ConstructorPage(WebDriver driver) {
        super(driver);
    }

    @Step("Перейти в раздел 'Булки'")
    public void goToBuns() {
        try {
            // Ждём кликабельности родительского div
            waitForElementClickable(bunTab);
            WebElement tab = driver.findElement(bunTab);
            // Пробуем кликнуть через JavaScript, если обычный клик не работает
            try {
                tab.click();
            } catch (Exception e) {
                // Если обычный клик не работает, используем JavaScript
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
            }
            System.out.println("Переход в раздел 'Булки'");
        } catch (Exception e) {
            System.out.println("Не удалось перейти в раздел 'Булки': " + e.getMessage());
            throw e;
        }
        waitForBunTabActive();
    }

    @Step("Перейти в раздел 'Соусы'")
    public void goToSauces() {
        try {
            waitForElementClickable(sauceTab);
            WebElement tab = driver.findElement(sauceTab);
            try {
                tab.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
            }
            System.out.println("Переход в раздел 'Соусы'");
        } catch (Exception e) {
            System.out.println("Не удалось перейти в раздел 'Соусы': " + e.getMessage());
            throw e;
        }
        waitForSauceTabActive();
    }

    @Step("Перейти в раздел 'Начинки'")
    public void goToFillings() {
        try {
            waitForElementClickable(fillingTab);
            WebElement tab = driver.findElement(fillingTab);
            try {
                tab.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
            }
            System.out.println("Переход в раздел 'Начинки'");
        } catch (Exception e) {
            System.out.println("Не удалось перейти в раздел 'Начинки': " + e.getMessage());
            throw e;
        }
        waitForFillingTabActive();
    }

    @Step("Получить текст активного таба")
    public String getActiveTabText() {
        try {
            waitForElementVisible(activeTabText);
            return driver.findElement(activeTabText).getText();
        } catch (Exception e) {
            try {
                waitForElementVisible(activeTab);
                return driver.findElement(activeTab).getText();
            } catch (Exception ex) {
                System.out.println("Не удалось получить текст активного таба");
                return "";
            }
        }
    }

    @Step("Проверить, что активный таб 'Булки'")
    public boolean isBunTabActive() {
        try {
            // Проверяем, что таб имеет класс tab_tab_type_current
            WebElement tab = driver.findElement(By.xpath(".//span[text()='Булки']/parent::div"));
            String tabClass = tab.getAttribute("class");
            return tabClass != null && tabClass.contains("tab_tab_type_current");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что активный таб 'Соусы'")
    public boolean isSauceTabActive() {
        try {
            WebElement tab = driver.findElement(By.xpath(".//span[text()='Соусы']/parent::div"));
            String tabClass = tab.getAttribute("class");
            return tabClass != null && tabClass.contains("tab_tab_type_current");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что активный таб 'Начинки'")
    public boolean isFillingTabActive() {
        try {
            WebElement tab = driver.findElement(By.xpath(".//span[text()='Начинки']/parent::div"));
            String tabClass = tab.getAttribute("class");
            return tabClass != null && tabClass.contains("tab_tab_type_current");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить видимость раздела 'Булки'")
    public boolean isBunSectionDisplayed() {
        try {
            return driver.findElement(bunSection).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить видимость раздела 'Соусы'")
    public boolean isSauceSectionDisplayed() {
        try {
            return driver.findElement(sauceSection).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить видимость раздела 'Начинки'")
    public boolean isFillingSectionDisplayed() {
        try {
            return driver.findElement(fillingSection).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Дождаться загрузки конструктора")
    public void waitForConstructorLoad() {
        try {
            waitForPageLoad();
            System.out.println("Страница загружена");
            waitForElementVisible(anyTab);
            System.out.println("Табы конструктора найдены");
        } catch (Exception e) {
            System.out.println("Конструктор не загрузился: " + e.getMessage());
            throw e;
        }
    }

    @Step("Дождаться активации таба 'Булки'")
    public void waitForBunTabActive() {
        try {
            wait.until(driver -> {
                WebElement tab = driver.findElement(By.xpath(".//span[text()='Булки']/parent::div"));
                String tabClass = tab.getAttribute("class");
                return tabClass != null && tabClass.contains("tab_tab_type_current");
            });
            System.out.println("Таб 'Булки' активирован");
        } catch (Exception e) {
            System.out.println("Таб 'Булки' не активировался");
        }
    }

    @Step("Дождаться активации таба 'Соусы'")
    public void waitForSauceTabActive() {
        try {
            wait.until(driver -> {
                WebElement tab = driver.findElement(By.xpath(".//span[text()='Соусы']/parent::div"));
                String tabClass = tab.getAttribute("class");
                return tabClass != null && tabClass.contains("tab_tab_type_current");
            });
            System.out.println("Таб 'Соусы' активирован");
        } catch (Exception e) {
            System.out.println("Таб 'Соусы' не активировался");
        }
    }

    @Step("Дождаться активации таба 'Начинки'")
    public void waitForFillingTabActive() {
        try {
            wait.until(driver -> {
                WebElement tab = driver.findElement(By.xpath(".//span[text()='Начинки']/parent::div"));
                String tabClass = tab.getAttribute("class");
                return tabClass != null && tabClass.contains("tab_tab_type_current");
            });
            System.out.println("Таб 'Начинки' активирован");
        } catch (Exception e) {
            System.out.println("Таб 'Начинки' не активировался");
        }
    }
}