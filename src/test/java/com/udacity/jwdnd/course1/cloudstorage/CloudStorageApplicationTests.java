package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void onlyLoginAndSignupAllowWithoutLogin() {
        String baseUrl = "http://localhost:" + this.port;
        driver.get(baseUrl + "/home");
        assertEquals(baseUrl + "/login", driver.getCurrentUrl());
        driver.get(baseUrl + "/login");
        assertEquals(baseUrl + "/login", driver.getCurrentUrl());
        driver.get(baseUrl + "/signup");
        assertEquals(baseUrl + "/signup", driver.getCurrentUrl());
    }

    @Test
    public void TestSignupWorkflow() {
        String firstName = "asdf";
        String lastName= "aassddff";
        String username = "t";
        String password = "a";
        String baseUrl = "http://localhost:" + this.port;

        driver.get(baseUrl + "/signup");
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.registerAccount(firstName, lastName, username, password);
        signupPage.followLoginLinkAfterSuccessfulRegistration(); // if this fails, signup fails.

        // new WebDriverWait(driver, 1).until(driver -> driver.findElement(By.id("submit-button")).isDisplayed());
        assertEquals(baseUrl + "/login", driver.getCurrentUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        assertEquals(baseUrl + "/home", driver.getCurrentUrl());
        HomePage homePage = new HomePage(driver);
        homePage.logout();

        assertEquals(baseUrl + "/login?logout", driver.getCurrentUrl());
        driver.get(baseUrl + "/home");
        assertEquals(baseUrl + "/login", driver.getCurrentUrl());
    }

    @Test
    public void TestNoteOperations() {
        String firstName = "asdf";
        String lastName= "aassddff";
        String username = "t";
        String password = "a";
        String baseUrl = "http://localhost:" + this.port;

        driver.get(baseUrl + "/signup");
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.registerAccount(firstName, lastName, username, password);
        signupPage.followLoginLinkAfterSuccessfulRegistration(); // if this fails, signup fails.

        assertEquals(baseUrl + "/login", driver.getCurrentUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        // get the home page
        // switch to the note tab
        HomePage homePage = new HomePage(driver);
        homePage.switchToNoteTab();

        // add a note,
        //   chenck that there is 1 in the list.
        //   check the title and the description
        String title = "First Title";
        String description = "First Description";
        String modifiedTitle = "Modified title";
        String modifiedDescription = "Modified Descriptions";

        homePage.addNewNotes(title, description);
        homePage.switchToNoteTab();
        List<WebElement> titles = driver.findElements(By.className("note-titles"));
        assertEquals(1, titles.size());

        assertEquals(title, titles.get(0).getAttribute("innerHTML"));
        assertEquals(description, driver.findElements(By.className("note-descriptions")).get(0).getText());

        //   click on edit note and change the content and save changes
        homePage.changeFirstNote(modifiedTitle, modifiedDescription);

        // the page should now be at the home page, switch to note tab
        //   check the title and description, make sure it changes the new title and description
        // delete the note and check the list is empty
        homePage.switchToNoteTab();
        titles = driver.findElements(By.className("note-titles"));
        assertEquals(1, titles.size());
        assertEquals(modifiedTitle, titles.get(0).getAttribute("innerHTML"));
        assertEquals(modifiedDescription, driver.findElements(By.className("note-descriptions")).get(0).getText());

        // Delete first not and check that there is no data in the list,
        // or getting the list should get no element exceiption.
        homePage.deleteFirstNote();
        titles = driver.findElements(By.className("note-titles")); // not found exceiption??
        assertEquals(0, titles.size());
    }

    @Test
    public void TestCredentialOperations() {
        String firstName = "asdf";
        String lastName= "aassddff";
        String username = "t";
        String password = "a";
        String baseUrl = "http://localhost:" + this.port;

        driver.get(baseUrl + "/signup");
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.registerAccount(firstName, lastName, username, password);
        signupPage.followLoginLinkAfterSuccessfulRegistration(); // if this fails, signup fails.

        assertEquals(baseUrl + "/login", driver.getCurrentUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        String firstUrl = "firstUrl.com";
        String firstUsername = "firstUsername";
        String firstPassword = "firstPassword";
        String secondUrl = "secondUrl.com";
        String secondUsername = "secondUsername";
        String secondPassword = "secondPassword";
        String modifiedFirstUrl = "modifiedFirstUrl.com";
        HomePage homePage = new HomePage(driver);
        homePage.addCredential(firstUrl, firstUsername, firstPassword);
        homePage.addCredential(secondUrl, secondUsername, secondPassword);
        List<Credential> credentials = homePage.getAllCredentials();
        assertEquals(2, credentials.size());
        assertEquals(firstUrl, credentials.get(0).getUrl());
        assertEquals(firstUsername, credentials.get(0).getUsername());
        assertNotEquals(firstPassword, credentials.get(0).getPassword());
        assertEquals(secondUrl, credentials.get(1).getUrl());
        assertEquals(secondUsername, credentials.get(1).getUsername());
        assertNotEquals(secondPassword, credentials.get(1).getPassword());

        homePage.showCredentialModal(0);
        assertEquals(firstUrl, driver.findElement(By.id("credential-url")).getAttribute("value"));
        assertEquals(firstUsername, driver.findElement(By.id("credential-username")).getAttribute("value"));
        assertEquals(firstPassword, driver.findElement(By.id("credential-password")).getAttribute("value"));
        homePage.closeCredentialModal();
        homePage.showCredentialModal(1);
        assertEquals(secondUrl, driver.findElement(By.id("credential-url")).getAttribute("value"));
        assertEquals(secondUsername, driver.findElement(By.id("credential-username")).getAttribute("value"));
        assertEquals(secondPassword, driver.findElement(By.id("credential-password")).getAttribute("value"));
        homePage.closeCredentialModal();

        homePage.updateCredential(0, modifiedFirstUrl, firstUsername, firstPassword);
        credentials = homePage.getAllCredentials();
        assertEquals(2, credentials.size());
        assertEquals(modifiedFirstUrl, credentials.get(0).getUrl());
        assertEquals(firstUsername, credentials.get(0).getUsername());
        assertNotEquals(firstPassword, credentials.get(0).getPassword());
        assertEquals(secondUrl, credentials.get(1).getUrl());
        assertEquals(secondUsername, credentials.get(1).getUsername());
        assertNotEquals(secondPassword, credentials.get(1).getPassword());

        homePage.showCredentialModal(0);
        assertEquals(modifiedFirstUrl, driver.findElement(By.id("credential-url")).getAttribute("value"));
        assertEquals(firstUsername, driver.findElement(By.id("credential-username")).getAttribute("value"));
        assertEquals(firstPassword, driver.findElement(By.id("credential-password")).getAttribute("value"));
        homePage.closeCredentialModal();
        homePage.showCredentialModal(1);
        assertEquals(secondUrl, driver.findElement(By.id("credential-url")).getAttribute("value"));
        assertEquals(secondUsername, driver.findElement(By.id("credential-username")).getAttribute("value"));
        assertEquals(secondPassword, driver.findElement(By.id("credential-password")).getAttribute("value"));
        homePage.closeCredentialModal();

        for(int i=0; i<credentials.size(); i++) {
            homePage.deleteCredential(0);
        }

        assertEquals(0, homePage.getAllCredentials().size());
    }
}
