package com.mahmoud.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddEmployeePage extends BasePage {

    private final By addEmployeeTitle =
            By.xpath("//h6[contains(@class,'orangehrm-main-title') and normalize-space()='Add Employee']");
    private final By firstNameField = By.name("firstName");
    private final By lastNameField = By.name("lastName");
    private final By saveButton = By.cssSelector("button[type='submit']");

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    public boolean isAddEmployeePageDisplayed() {
        return isDisplayed(addEmployeeTitle);
    }

    public AddEmployeePage enterFirstName(String firstName) {
        type(firstNameField, firstName);
        return this;
    }

    public AddEmployeePage enterLastName(String lastName) {
        type(lastNameField, lastName);
        return this;
    }

    public boolean save() {
        click(saveButton);
        try {
            return wait.until(ExpectedConditions.urlContains("/pim/viewPersonalDetails"));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getSavedFirstName() {
        wait.until(driver -> {
            String value = driver.findElement(firstNameField).getAttribute("value");
            return value != null && !value.isEmpty();
        });
        return driver.findElement(firstNameField).getAttribute("value");
    }
}