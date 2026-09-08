package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class DriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    private static final String YANDEX_BROWSER_PATH = "C:/Program Files/Yandex/YandexBrowser/Application/browser.exe";

    static {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        System.setProperty("webdriver.chrome.verboseLogging", "false");
        System.setProperty("wdm.quiet", "true");
    }

    public static WebDriver getDriver(String browser) {
        ChromeOptions options = getChromeOptions();
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "yandex":
                logger.info("Запуск Яндекс.Браузера");
                WebDriverManager.chromedriver()
                        .browserVersion(YANDEX_BROWSER_PATH)
                        .clearDriverCache()
                        .clearResolutionCache()
                        .setup();
                options.setBinary(YANDEX_BROWSER_PATH);
                driver = new ChromeDriver(options);
                break;
            default:
                logger.info("Запуск Google Chrome");
                WebDriverManager.chromedriver()
                        .clearDriverCache()
                        .clearResolutionCache()
                        .setup();
                driver = new ChromeDriver(options);
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        logger.info("Браузер успешно запущен");
        return driver;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--log-level=3");
        options.addArguments("--silent");
        options.addArguments("--disable-logging");

        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        return options;
    }
}