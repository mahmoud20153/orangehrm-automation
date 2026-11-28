package com.mahmoud.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for capturing screenshots.
 * Kept separate from Page Objects to maintain Single Responsibility Principle.
 */
public final class ScreenshotUtil {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "target" + File.separator + "screenshots";

    private ScreenshotUtil() {
        // Utility class - prevent instantiation
    }

    /**
     * Captures a screenshot of the current browser state and saves it to target/screenshots.
     *
     * @param driver  The WebDriver instance to capture from.
     * @param testName The name of the test (used for the filename).
     * @return The absolute path to the saved screenshot file, or empty string if failed.
     */
    public static String capture(WebDriver driver, String testName) {
        try {
            // 1. Cast driver to TakesScreenshot interface
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            // 2. Create the screenshots directory if it doesn't exist
            Path dirPath = Paths.get(SCREENSHOT_DIR);
            Files.createDirectories(dirPath);

            // 3. Generate a unique filename with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            Path destPath = dirPath.resolve(fileName);

            // 4. Copy the file to the destination
            Files.copy(source.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);

            log.info("Screenshot saved successfully: {}", destPath.toAbsolutePath());
            return destPath.toAbsolutePath().toString();

        } catch (IOException e) {
            log.error("Failed to capture screenshot for test: {}", testName, e);
            return "";
        }
    }

    public static byte[] captureBytes(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}