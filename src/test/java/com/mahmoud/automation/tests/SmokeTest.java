package com.mahmoud.automation.tests;

import com.mahmoud.automation.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test(description = "Verify OrangeHRM login page loads with correct title")
    public void loginPageLoadsSuccessfully() {
        String actualTitle = driver.getTitle();
        log.info("Actual page title: {}", actualTitle);
        Assert.assertTrue(actualTitle.contains("OrangeHRM"),
                "Login page title mismatch! Actual: " + actualTitle);
    }
}