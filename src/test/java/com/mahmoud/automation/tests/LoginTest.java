package com.mahmoud.automation.tests;

import com.mahmoud.automation.base.BaseTest;
import com.mahmoud.automation.data.LoginData;
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

    @Test(dataProvider = "validationCases", dataProviderClass = LoginData.class,
            description = "Missing credentials show 'Required' validation under the empty fields")
    public void missingFieldsShowRequiredValidation(String caseName, String username,
                                                    String password, List<String> expectedMessages) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.attemptLogin(username, password);

        Assert.assertEquals(loginPage.getValidationMessages(), expectedMessages,
                "Unexpected validation messages for case: " + caseName);
        log.info("Case [{}] rejected with {}", caseName, expectedMessages);
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

    @Test(dataProvider = "invalidCredentials", dataProviderClass = LoginData.class,
            description = "Wrong credentials show 'Invalid credentials' and keep the user on Login")
    public void invalidCredentialsShowError(String caseName, String username, String password) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.attemptLogin(username, password);

        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid credentials",
                "Unexpected error message for case: " + caseName);
        Assert.assertTrue(driver.getCurrentUrl().contains("/auth/login"),
                "User should stay on the Login page for case: " + caseName);
        log.info("Case [{}] correctly rejected", caseName);
    }

}