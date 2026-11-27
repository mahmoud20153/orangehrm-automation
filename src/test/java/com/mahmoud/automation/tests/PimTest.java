package com.mahmoud.automation.tests;

import com.mahmoud.automation.base.BaseTest;
import com.mahmoud.automation.pages.PimAddEmployeePage;
import com.mahmoud.automation.pages.DashboardPage;
import com.mahmoud.automation.pages.PimEmployeeListPage;
import com.mahmoud.automation.pages.PimPersonalDetailsPage;
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

    @Test(description = "Adding an employee with mandatory fields opens Personal Details")
    public void userCanAddNewEmployee() {
        String firstName  = "AutoFirst";
        String lastName   = "Auto" + System.currentTimeMillis();
        String employeeId = String.valueOf(System.currentTimeMillis()).substring(4); // 9 digits, fits limit

        DashboardPage dashboard = loginAsAdmin();
        PimEmployeeListPage pimList = dashboard.openPimEmployeeList();
        PimAddEmployeePage addPage = pimList.clickAdd();

        PimPersonalDetailsPage detailPage = addPage
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmployeeId(employeeId)
                .save();

        Assert.assertTrue(detailPage.isOnPersonalDetailsPage(),
                "Personal Details page was not displayed after saving");
        Assert.assertTrue(detailPage.getDisplayedEmployeeName().contains(firstName),
                "First name not in displayed employee name");
        Assert.assertTrue(detailPage.getDisplayedEmployeeName().contains(lastName),
                "Last name not in displayed employee name");

        log.info("Employee created successfully: {} {} (ID: {}, length: {})",
                firstName, lastName, employeeId, employeeId.length());
    }
}