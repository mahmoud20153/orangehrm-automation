package com.mahmoud.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class PimEmployeeListPage extends BasePage {

    private final By addButton = By.xpath("//button[normalize-space()='Add']");

    public PimEmployeeListPage(WebDriver driver) {
        super(driver);
    }

    public boolean isEmployeeListDisplayed() {
        return isDisplayed(addButton);
    }

    public PimAddEmployeePage clickAdd() {
        click(addButton);
        wait.until(ExpectedConditions.urlContains("/pim/addEmployee"));
        return new PimAddEmployeePage(driver);
    }
}