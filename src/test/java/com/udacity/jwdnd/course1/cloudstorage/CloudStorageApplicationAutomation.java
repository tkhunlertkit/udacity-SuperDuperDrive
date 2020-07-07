package com.udacity.jwdnd.course1.cloudstorage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CloudStorageApplicationAutomation {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        try {
            routineOperations(driver);
        } finally {
            driver.quit();
        }
    }

    public static void routineOperations(WebDriver driver) {
        String firstName = "asdf";
        String lastName= "aassddff";
        String username = "t";
        String password = "a";

        driver.get("localhost:8080/signup");
        SignUpPage signupPage = new SignUpPage(driver);
        try {
            signupPage.registerAccount(firstName, lastName, username, password);
        } catch (Exception e) {}
        // signupPage.followLoginLinkAfterSuccessfulRegistration();

        driver.get("localhost:8080/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        // add notes
        List<TempNote> notes = new ArrayList<>();
        notes.add(new TempNote("Title1", "Description1"));
        notes.add(new TempNote("Title2", "Description2"));
        notes.add(new TempNote("Title3", "Description3"));
        notes.add(new TempNote("Title4", "Description4"));
        HomePage homePage = new HomePage(driver);
        homePage.switchToNoteTab();
        for (TempNote n : notes) {
            homePage = new HomePage(driver);
            homePage.addNewNotes(n.title, n.description);
        }

        homePage.uploadFile("/Users/tkhunlertkit/Desktop/test.png");
        homePage.uploadFile("/Users/tkhunlertkit/Desktop/test.png");
        homePage.uploadFile("/Users/tkhunlertkit/Desktop/test.png");
    }
}