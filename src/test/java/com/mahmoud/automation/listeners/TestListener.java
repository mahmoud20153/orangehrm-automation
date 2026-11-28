package com.mahmoud.automation.listeners;

import com.mahmoud.automation.drivers.DriverFactory;
import com.mahmoud.automation.utils.ScreenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class TestListener implements ITestListener {

    private static final Logger log = LoggerFactory.getLogger(TestListener.class);

    /** Test name + first parameter (the case name) when a DataProvider is used. */
    private String displayName(ITestResult result) {
        Object[] params = result.getParameters();
        return params.length > 0 ? result.getName() + "[" + params[0] + "]" : result.getName();
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("▶ STARTING TEST: {}", displayName(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info(" PASSED: {}", displayName(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String name = displayName(result);
        log.error(" FAILED: {}", name);
        try {
            WebDriver driver = DriverFactory.getDriver();

            String path = ScreenshotUtil.capture(driver, name);
            if (!path.isEmpty()) {
                log.error("Screenshot saved at: {}", path);
            }

            Allure.addAttachment("Screenshot on failure", "image/png",
                    new ByteArrayInputStream(ScreenshotUtil.captureBytes(driver)), "png");
        } catch (Exception e) {
            log.warn("Could not capture screenshot for {}: {}", name, e.getMessage());
        }
    }

    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}