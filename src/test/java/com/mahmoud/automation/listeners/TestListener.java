package com.mahmoud.automation.listeners;

import com.mahmoud.automation.drivers.DriverFactory;
import com.mahmoud.automation.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener that hooks into the test lifecycle.
 * Automatically captures a screenshot the moment a test fails.
 */
public class TestListener implements ITestListener {

    private static final Logger log = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("▶ STARTING TEST: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info(" PASSED: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error(" FAILED: {}", result.getName());

        // Get the driver for the CURRENT thread (ThreadLocal safety)
        WebDriver driver = DriverFactory.getDriver();

        // Capture the screenshot
        String screenshotPath = ScreenshotUtil.capture(driver, result.getName());

        if (!screenshotPath.isEmpty()) {
            log.error("Screenshot attached at: {}", screenshotPath);
        }
    }

    // We don't need the other methods for now, but they must be implemented (can be empty)
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}