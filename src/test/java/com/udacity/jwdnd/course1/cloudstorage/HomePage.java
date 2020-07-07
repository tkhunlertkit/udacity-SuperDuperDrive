package com.udacity.jwdnd.course1.cloudstorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    // Note Section
    @FindBy(id = "add-note-button") private WebElement addNoteButton;
    @FindBy(id = "note-id") private WebElement modalNoteId;
    @FindBy(id = "note-title") private WebElement modalNoteTitle;
    @FindBy(id = "note-description") private WebElement modalNoteDescription;
    @FindBy(id = "note-save-button") private WebElement modalNoteSaveButton;
    @FindBy(id = "nav-notes-tab") private WebElement navNotesTab;
    @FindBy(className = "delete-notes") private List<WebElement> noteDeleteButtons;
    @FindBy(className = "edit-notes") private List<WebElement> noteEditButtons;

    // File Section
    @FindBy(id = "nav-files-tab") private WebElement navFileTab;
    @FindBy(id = "fileUpload") private WebElement fileUploadSelector;
    @FindBy(id = "fileUploadSubmit") private WebElement fileUploadSubmit;

    // Credential Section
    @FindBy(id = "nav-credentials-tab") private WebElement navCredentialTab;
    @FindBy(id = "add-credential-button") private WebElement addCredentialButton;
    @FindBy(id = "credential-url") private WebElement modalCredentialUrl;
    @FindBy(id = "credential-username") private WebElement modalCredentialUsername;
    @FindBy(id = "credential-password") private WebElement modalCredentialPassword;
    @FindBy(id = "credential-save") private WebElement modalCredentialSave;
    @FindBy(id = "credential-close") private WebElement modalCredentialClose;
    @FindBy(className = "edit-credentials") private List<WebElement> editCredential;
    @FindBy(className = "delete-credentials") private List<WebElement> deleteCredentials;
    @FindBy(className = "credential-urls") private List<WebElement> credentialUrls;
    @FindBy(className = "credential-usernames") private List<WebElement> credentialUsernames;
    @FindBy(className = "credential-passwords") private List<WebElement> credentialPasswords;

    // Logout
    @FindBy(id = "logout-button") private WebElement logoutButton;

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //
    // Logout
    public void logout() {
        logoutButton.click();
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }

    //
    // Note Section
    public void addNewNotes(String title, String description) {
        switchToNoteTab();
        addNoteButton.click();
        waitUntilShow("note-save-button");
        modifyCurrentNote(title, description);
    }

    public void switchToNoteTab() {
        try {navNotesTab.click(); } catch(Exception e) {}
        waitUntilShow("add-note-button");
    }

    public void deleteFirstNote() {
        switchToNoteTab();
        noteDeleteButtons.get(0).click();
        waitUntilShow("nav-notes-tab");
    }

    public void changeFirstNote(String title, String description) {
        switchToNoteTab();
        noteEditButtons.get(0).click();
        waitUntilShow("note-save-button");
        modifyCurrentNote(title, description);
    }

    private void modifyCurrentNote(String title, String description) {
        modalNoteTitle.clear();
        modalNoteDescription.clear();
        modalNoteTitle.sendKeys(title);
        modalNoteDescription.sendKeys(description);
        modalNoteSaveButton.click();
        waitUntilShow("nav-notes-tab");
    }

    //
    // File Section
    public void switchToFileTab() {
        try { navFileTab.click(); } catch(Exception e) {}
        waitUntilShow("fileUpload");
    }

    public void uploadFile(String filePath) {
        switchToFileTab();
        fileUploadSelector.sendKeys("/Users/tkhunlertkit/Desktop/test.png");
        fileUploadSubmit.click();
        waitUntilShow("fileUpload");
    }

    //
    // Credential Section
    public void switchToCredentialTab() {
        try { navCredentialTab.click(); } catch(Exception e) {}
        waitUntilShow("add-credential-button");
    }

    public void addCredential(String url, String username, String password) {
        switchToCredentialTab();
        addCredentialButton.click();
        updateCredentialModal(url, username, password);
    }

    public void updateCredential(int index, String url, String username, String password) {
        showCredentialModal(index);
        updateCredentialModal(url, username, password);
    }

    private void updateCredentialModal(String url, String username, String password) {
        waitUntilShow("credential-save");
        modalCredentialUrl.clear();
        modalCredentialUsername.clear();
        modalCredentialPassword.clear();
        modalCredentialUrl.sendKeys(url);
        modalCredentialUsername.sendKeys(username);
        modalCredentialPassword.sendKeys(password);
        modalCredentialSave.click();
        waitUntilShow("nav-credentials-tab");
    }

    public List<Credential> getAllCredentials() {
        switchToCredentialTab();
        List<Credential> res = new ArrayList<>();
        for (int i : IntStream.range(0, credentialUrls.size()).toArray()) {
            res.add(new Credential(i, credentialUrls.get(i).getAttribute("innerHTML"),
                credentialUsernames.get(i).getAttribute("innerHTML"),
                credentialPasswords.get(i).getAttribute("innerHTML")));
        }
        return res;
    }

    public void showCredentialModal(int index) {
        switchToCredentialTab();
        editCredential.get(index).click();
        waitUntilShow("credential-save");
    }

    public void closeCredentialModal() {
        try { modalCredentialClose.click(); } catch(Exception e) {}
        waitUntilShow("nav-credentials-tab");
    }

    public void deleteCredential(int index) {
        switchToCredentialTab();
        deleteCredentials.get(index).click();
        waitUntilShow("nav-credentials-tab");
    }

    //
    // Private methods
    private void waitUntilShow(String id) {
        try { Thread.sleep(500); } catch(Exception e) {}
        new WebDriverWait(this.driver, 1).until(e -> e.findElement(By.id(id)).isDisplayed());
    }
}
