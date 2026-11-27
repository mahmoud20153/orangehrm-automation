package com.mahmoud.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class PimAddEmployeePage extends BasePage {

    private final By firstNameInput   = By.cssSelector("input[placeholder='First Name']");
    private final By lastNameInput    = By.cssSelector("input[placeholder='Last Name']");
    private final By saveButton       = By.xpath("//button[normalize-space()='Save']");
    private final By validationMessage = By.cssSelector(".oxd-input-field-error-message");
    private final By employeeIdInput = By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");
    public PimAddEmployeePage(WebDriver driver) {
        super(driver);
    }

    public boolean isAddEmployeePageDisplayed() {
        return isDisplayed(firstNameInput) && isDisplayed(lastNameInput);
    }

    public PimAddEmployeePage enterFirstName(String name) {
        type(firstNameInput, name);
        return this;
    }

    public PimAddEmployeePage enterLastName(String name) {
        type(lastNameInput, name);
        return this;
    }

    /** Raw click — stays on same page. For negative tests. */
    public PimAddEmployeePage clickSave() {
        click(saveButton);
        return this;
    }

    public PimPersonalDetailsPage save() {
        click(saveButton);
        wait.until(ExpectedConditions.urlContains("/pim/viewPersonalDetails"));
        return new PimPersonalDetailsPage(driver);
    }

    public PimAddEmployeePage enterEmployeeId(String employeeId) {
        WebElement field = waitForVisible(employeeIdInput);
        field.click();
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        field.sendKeys(employeeId);
        return this;
    }
}