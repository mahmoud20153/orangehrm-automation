package com.mahmoud.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashboardPage extends BasePage {

    // Use the CSS class instead of text - much more stable in SPAs
    private final By dashboardTitle = By.cssSelector(".oxd-topbar-header-breadcrumb-module");
    private final By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
    private final By logoutLink =
            By.xpath("//a[@class='oxd-userdropdown-link' and normalize-space()='Logout']");
    private final By pimMenu = By.cssSelector("a.oxd-main-menu-item[href*='viewPimModule']");
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardDisplayed() {
        return isDisplayed(dashboardTitle);
    }

    public String getDashboardTitle() {
        return getText(dashboardTitle);
    }

    public DashboardPage openUserMenu() {
        click(userDropdown);
        return this;
    }

    public LoginPage logout() {
        openUserMenu();                       // reuse your fluent method
        click(logoutLink);                    // your locator
        wait.until(ExpectedConditions.urlContains("/auth/login"));  // prove navigation
        return new LoginPage(driver);         // user is now on Login page
    }

    public PimEmployeeListPage openPimEmployeeList() {
        click(pimMenu);
        return new PimEmployeeListPage(driver);
    }
}