package com.mahmoud.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LoginPage extends BasePage {

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton   = By.cssSelector("button[type='submit']");
    private final By errorMessage  = By.cssSelector("div.oxd-alert--error");
    private final By validationMessage = By.cssSelector(".oxd-input-field-error-message");
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public void submit() {
        click(loginButton);
    }

    public DashboardPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        submit();

        // This proves the login API call succeeded and the router navigated
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        return new DashboardPage(driver);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public LoginPage emptyLogin() {
        click(loginButton);
        return this;
    }

    public boolean isEmptyErrorMessageDisplayed() {
        return isDisplayed(validationMessage);
    }

    public List<String> getValidationMessages() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(validationMessage));
        return driver.findElements(validationMessage).stream()
                .map(e -> e.getText().trim())
                .toList();
    }

}