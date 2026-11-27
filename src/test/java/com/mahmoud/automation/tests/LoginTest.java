package com.mahmoud.automation.tests;

import com.mahmoud.automation.base.BaseTest;
import com.mahmoud.automation.pages.DashboardPage;
import com.mahmoud.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
public class LoginTest extends BaseTest {

    // Test data lives here for now - Phase 6 moves it to a DataProvider
    private static final String VALID_USERNAME = "Admin";
    private static final String VALID_PASSWORD = "admin123";

    @Test(description = "Valid credentials should land the user on the Dashboard")
    public void validLoginNavigatesToDashboard() {
        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboardPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);

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

        // 1. Login
        DashboardPage dashboardPage = loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(),
                "Precondition failed: Dashboard was not displayed after login");

        // 2. Logout
        LoginPage returnedLoginPage = dashboardPage.logout();

        // 3. Verify
        Assert.assertTrue(driver.getCurrentUrl().contains("/auth/login"),
                "User was not redirected to Login page after logout");
        log.info("Logout successful - user returned to Login page");
    }

    @Test(description = "User can not log in with empty fields")
    public void userCanNotLogInWithEmptyFields() {
      LoginPage loginPage = new LoginPage(driver);
      loginPage.emptyLogin();
        Assert.assertTrue(loginPage.isEmptyErrorMessageDisplayed(),
                "Validation message was not displayed after submitting empty fields");
        log.info("Empty fields correctly rejected with validation message");
    }



}