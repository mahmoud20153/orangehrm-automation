package com.mahmoud.automation.tests;

import com.mahmoud.automation.base.BaseTest;
import com.mahmoud.automation.pages.DashboardPage;
import com.mahmoud.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class LoginTest extends BaseTest {

    @Test(description = "Valid credentials should land the user on the Dashboard")
    public void validLoginNavigatesToDashboard() {
        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboardPage = loginAsAdmin();

        Assert.assertTrue(dashboardPage.isDashboardDisplayed(),
                "Dashboard was not displayed after a valid login");
        log.info("Valid login successful - Dashboard displayed");
    }

    @Test(description = "Invalid credentials should show 'Invalid credentials' error")
    public void invalidLoginShowsErrorMessage() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername("WrongUser")
                .enterPassword("WrongPass")
                .submit();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message was not displayed after invalid login");
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid credentials",
                "Unexpected error message text");
        log.info("Invalid login correctly rejected with error message");
    }

    @Test(description = "Logged-in user should be redirected to Login page after logout")
    public void userCanLogoutSuccessfully(){
        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboardPage = loginAsAdmin();
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(),
                "Precondition failed: Dashboard was not displayed after login");

        // 2. Logout
        dashboardPage.logout();
        // 3. Verify
        Assert.assertTrue(driver.getCurrentUrl().contains("/auth/login"),
                "User was not redirected to Login page after logout");
        log.info("Logout successful - user returned to Login page");
    }

    @Test(description = "Empty username and password show 'Required' under both fields")
    public void userCannotLoginWithEmptyFields() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.emptyLogin();

        Assert.assertEquals(loginPage.getValidationMessages(), List.of("Required", "Required"),
                "Expected a 'Required' message under both fields");
        log.info("Empty fields correctly rejected with validation messages");
    }

}