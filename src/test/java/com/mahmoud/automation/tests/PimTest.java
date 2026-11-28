package com.mahmoud.automation.tests;

import com.mahmoud.automation.base.BaseTest;
import com.mahmoud.automation.data.PimData;
import com.mahmoud.automation.pages.PimAddEmployeePage;
import com.mahmoud.automation.pages.DashboardPage;
import com.mahmoud.automation.pages.PimEmployeeListPage;
import com.mahmoud.automation.pages.PimPersonalDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

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

    @Test(dataProvider = "missingNameCases", dataProviderClass = PimData.class,
            description = "Missing mandatory names show 'Required' and keep the user on Add Employee")
    public void missingNamesShowRequiredValidation(String caseName, String firstName,
                                                   String lastName, List<String> expectedMessages) {
        PimAddEmployeePage addPage = loginAsAdmin()
                .openPimEmployeeList()
                .clickAdd();

        addPage.enterFirstName(firstName)
                .enterLastName(lastName)
                .clickSave();

        Assert.assertEquals(addPage.getValidationMessages(), expectedMessages,
                "Unexpected validation messages for case: " + caseName);
        Assert.assertTrue(addPage.isAddEmployeePageDisplayed(),
                "User should stay on Add Employee for case: " + caseName);
        log.info("Case [{}] rejected with {}", caseName, expectedMessages);
    }

    @Test(description = "Adding an employee with an existing Employee Id shows an error and is not saved")
    public void duplicateEmployeeIdIsRejected() {
        String employeeId = String.valueOf(System.currentTimeMillis()).substring(4);   // 9 digits
        String lastName = "Dup" + (System.currentTimeMillis() % 100000);

        DashboardPage dashboard = loginAsAdmin();

        // 1. Create the first employee with a known ID
        dashboard.openPimEmployeeList()
                .clickAdd()
                .enterFirstName("First")
                .enterLastName(lastName)
                .enterEmployeeId(employeeId)
                .save();

        // create a second employee with the same ID
        PimAddEmployeePage addPage = dashboard.openPimEmployeeList().clickAdd();
        addPage.enterFirstName("Second")
                .enterLastName(lastName)
                .enterEmployeeId(employeeId);

        Assert.assertEquals(addPage.getEmployeeIdError(), "Employee Id already exists",
                "Unexpected error message for a duplicate Employee Id");

        addPage.clickSave();
        Assert.assertTrue(addPage.isAddEmployeePageDisplayed(),
                "User should stay on Add Employee when the Employee Id is duplicated");
        log.info("Duplicate Employee Id {} correctly rejected", employeeId);
    }
}