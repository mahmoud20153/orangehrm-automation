package com.mahmoud.automation.drivers;

import com.mahmoud.automation.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Thread-safe WebDriver factory.
 * Relies on Selenium 4's built-in Selenium Manager for automatic driver resolution.
 */
public final class DriverFactory {

    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    private DriverFactory() {
    }

    /** Creates a new driver for the current thread and stores it in the ThreadLocal pool. */
    public static WebDriver createDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();
        log.info("Launching browser: {}", browser);

        WebDriver driver = switch (browser) {
            case "chrome" -> createChromeDriver();
            case "firefox" -> createFirefoxDriver();
            case "edge" -> createEdgeDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };

        configureDriver(driver);
        driverPool.set(driver);
        return driver;
    }

    /** Returns the driver belonging to the current thread. */
    public static WebDriver getDriver() {
        WebDriver driver = driverPool.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver not initialized for this thread. Call createDriver() first.");
        }
        return driver;
    }

    /** Quits the driver and removes it from the pool (prevents memory leaks). */
    public static void quitDriver() {
        WebDriver driver = driverPool.get();
        if (driver != null) {
            driver.quit();
            driverPool.remove();
            log.info("Browser closed and driver removed from pool");
        }
    }

    // ---------- private builders ----------

    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        if (ConfigReader.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-notifications", "--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        if (ConfigReader.getBoolean("headless")) {
            options.addArguments("-headless");
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        if (ConfigReader.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        return new EdgeDriver(options);
    }

    private static void configureDriver(WebDriver driver) {
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
        // Best practice: zero implicit waits - we use explicit waits only (Phase 4)
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        if (ConfigReader.getBoolean("window.maximize")) {
            driver.manage().window().maximize();
        }
    }
}