package com.udacity.jwdnd.course1.cloudstorage;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {

    @FindBy(xpath = "//a[text()='Back to Login']") private WebElement loginLink;
    @FindBy(id = "inputFirstName") private WebElement firstName;
    @FindBy(id = "inputLastName") private WebElement lastName;
    @FindBy(id = "inputUsername") private WebElement username;
    @FindBy(id = "inputPassword") private WebElement password;
    @FindBy(id = "submit-button") private WebElement submitButton;
    private WebDriver driver;
    private WebDriverWait wait;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 3);
    }

    public void registerAccount(String firstName, String lastName, String username, String password) {
        clearAllInputField();
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.submitButton.click();
        try { Thread.sleep(500); } catch (Exception e) {}
        this.wait = new WebDriverWait(driver, 1);
        this.wait.until(d -> d.findElement(By.id("login-link")));
    }

    public void followLoginLinkAfterSuccessfulRegistration() throws NoSuchElementException {
        driver.findElement(By.id("login-link")).click();
        try { Thread.sleep(500); } catch (Exception e) {}
        this.wait = new WebDriverWait(driver, 1);
        this.wait.until(d -> !d.getCurrentUrl().contains("signup"));
    }

    public String getErrorMessage() throws NoSuchElementException {
        return driver.findElement(By.id("error-msg")).getText();
    }

    // Helper Methods
    private void clearAllInputField() {
        firstName.clear();
        lastName.clear();
        username.clear();
        password.clear();
    }
}