package com.mahmoud.automation.tests;

import com.mahmoud.automation.base.BaseTest;
import com.mahmoud.automation.pages.AddEmployeePage;
import com.mahmoud.automation.pages.DashboardPage;
import com.mahmoud.automation.pages.LoginPage;
import com.mahmoud.automation.pages.PimEmployeeListPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PimTest extends BaseTest {

    @Test(description = "PIM module is accessible from the Dashboard sidebar")
    public void verifyPimEmployeeListOpens() {
        DashboardPage dashboard = loginAsAdmin();
        PimEmployeeListPage pimList = dashboard.openPimEmployeeList();

        Assert.assertTrue(pimList.isEmployeeListDisplayed(),
                "PIM employee list was not displayed after navigating from Dashboard");
        log.info("Successfully navigated to PIM employee list");
    }

    @Test(description = "User can add a new employee from the PIM module")
    public void userCanAddNewEmployee() {
        String firstName = "Mahmoud";
        String lastName = "Reda";

        DashboardPage dashboard = loginAsAdmin();
        PimEmployeeListPage pimList = dashboard.openPimEmployeeList();

        AddEmployeePage addPage = pimList.clickAdd();
        Assert.assertTrue(addPage.isAddEmployeePageDisplayed(),
                "Add Employee page was not displayed");

        boolean saved = addPage.enterFirstName(firstName)
                .enterLastName(lastName)
                .save();

        Assert.assertTrue(saved, "Employee was not saved - Personal Details page did not open");
        Assert.assertEquals(addPage.getSavedFirstName(), firstName,
                "Saved first name does not match the entered one");
        log.info("Employee created and verified: " + firstName + " " + lastName);
    }
}