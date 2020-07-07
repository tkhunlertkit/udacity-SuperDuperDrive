package com.udacity.jwdnd.course1.cloudstorage;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    @FindBy(id = "inputUsername") private WebElement username;
    @FindBy(id = "inputPassword") private WebElement password;
    @FindBy(id = "submit-button") private WebElement loginButton;
    @FindBy(id = "signup-link") private WebElement signUpText;
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void login(String username, String password) {
        this.username.clear();
        this.username.sendKeys(username);
        this.password.clear();
        this.password.sendKeys(password);
        this.loginButton.click();
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        WebDriverWait wait = new WebDriverWait(this.driver, 3);
        wait.until(d -> !d.getCurrentUrl().contains("login"));
    }

    public void toSignUpPage() {
        this.signUpText.click();
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        WebDriverWait wait = new WebDriverWait(this.driver, 3);
        wait.until(d -> !d.getCurrentUrl().contains("login"));
    }

    public String getErrorMessage() throws NoSuchElementException {
        return driver.findElement(By.id("error-msg")).getText();
    }
}