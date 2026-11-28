package com.mahmoud.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for the employee's Personal Details screen.
 * Landing page after a successful employee creation.
 */
public class PimPersonalDetailsPage extends BasePage {

    private final By employeeNameHeader = By.cssSelector(".orangehrm-edit-employee-name h6");

    public PimPersonalDetailsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnPersonalDetailsPage() {
        try {
            return wait.until(ExpectedConditions.urlContains("/pim/viewPersonalDetails"));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getDisplayedEmployeeName() {
   wait.until(d -> !d.findElement(employeeNameHeader).getText().trim().isEmpty());
    return getText(employeeNameHeader);
    }
}

