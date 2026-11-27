package com.mahmoud.automation.base;

import com.mahmoud.automation.config.ConfigReader;
import com.mahmoud.automation.drivers.DriverFactory;
import com.mahmoud.automation.pages.DashboardPage;
import com.mahmoud.automation.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for all tests: handles driver lifecycle per test method.
 */
public abstract class BaseTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected WebDriver driver;

    protected DashboardPage loginAsAdmin() {
        return new LoginPage(driver).login(
                ConfigReader.get("valid.username"),
                ConfigReader.get("valid.password"));
    }

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(ConfigReader.get("app.url"));
        log.info("Navigated to: {}", ConfigReader.get("app.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}