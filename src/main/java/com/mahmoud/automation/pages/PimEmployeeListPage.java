package com.mahmoud.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PimEmployeeListPage extends BasePage {

    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By addEmployeeTitle =
            By.xpath("//h6[contains(@class,'orangehrm-main-title') and normalize-space()='Add Employee']");
    public PimEmployeeListPage(WebDriver driver) {
        super(driver);
    }

    public boolean isEmployeeListDisplayed() {
        return isDisplayed(addButton);
    }

    public AddEmployeePage clickAdd() {
        click(addButton);
        return new AddEmployeePage(driver);
    }


}