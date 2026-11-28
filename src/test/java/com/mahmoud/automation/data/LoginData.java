package com.mahmoud.automation.data;

import com.mahmoud.automation.config.ConfigReader;
import org.testng.annotations.DataProvider;

import java.util.List;

public class LoginData {

    /** Frontend validation: {caseName, username, password, expectedMessages} */
    @DataProvider(name = "validationCases")
    public static Object[][] validationCases() {
        String validUser = ConfigReader.get("valid.username");
        String validPass = ConfigReader.get("valid.password");
        return new Object[][]{
                {"both_empty",       "",        "",        List.of("Required", "Required")},
                {"username_empty",   "",        validPass, List.of("Required")},
                {"password_empty",   validUser, "",        List.of("Required")}
        };
    }

    /** Server-side rejection: {caseName, username, password} */
    @DataProvider(name = "invalidCredentials")
    public static Object[][] invalidCredentials() {
        String validUser = ConfigReader.get("valid.username");
        String validPass = ConfigReader.get("valid.password");
        return new Object[][]{
                {"wrong_user_wrong_pass", "WrongUser", "WrongPass"},
                {"valid_user_wrong_pass", validUser,   "WrongPass"},
                {"wrong_user_valid_pass", "WrongUser", validPass}
        };
    }
}