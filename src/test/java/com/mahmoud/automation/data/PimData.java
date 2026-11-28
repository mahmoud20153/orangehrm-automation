package com.mahmoud.automation.data;

import org.testng.annotations.DataProvider;

import java.util.List;

public class PimData {

    /** {caseName, firstName, lastName, expectedMessages} */
    @DataProvider(name = "missingNameCases")
    public static Object[][] missingNameCases() {
        return new Object[][]{
                {"both_names_empty", "",      "",     List.of("Required", "Required")},
                {"first_name_empty", "",      "Reda", List.of("Required")},
                {"last_name_empty",  "Ahmed", "",     List.of("Required")}
        };
    }
}