package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
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

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// request homepage before login
		driver.get("http://localhost:" + this.port + "/home");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		String pageSource = driver.getPageSource();
		Assertions.assertTrue(pageSource.contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}



	@Test
	public void testAddingNote() {
		doMockSignUp("chien","pham","admin12","123");
		doLogIn("admin12", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement btnTabNote = driver.findElement(By.id("nav-notes-tab"));
		btnTabNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-note-modal")));
		WebElement btnShowNoteModal = driver.findElement(By.id("btn-show-note-modal"));
		btnShowNoteModal.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement myNoteTitle = driver.findElement(By.id("note-title"));
		myNoteTitle.click();
		myNoteTitle.sendKeys("noteTitle");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement myNoteDescription = driver.findElement(By.id("note-description"));
		myNoteDescription.click();
		myNoteDescription.sendKeys("noteDesc");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-change")));
		WebElement btnSaveNote = driver.findElement(By.id("save-note-change"));
		btnSaveNote.click();
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertTrue(driver.getPageSource().contains("noteTitle"));
	}

	@Test
	public void testEditNote() {
		doMockSignUp("EditNote","Test","EditNote123","123");
		doLogIn("EditNote123", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement btnTabNote = driver.findElement(By.id("nav-notes-tab"));
		btnTabNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-note-modal")));
		WebElement btnShowNoteModal = driver.findElement(By.id("btn-show-note-modal"));
		btnShowNoteModal.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement myNoteTitle = driver.findElement(By.id("note-title"));
		myNoteTitle.click();
		myNoteTitle.sendKeys("Title1");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement myNoteDescription = driver.findElement(By.id("note-description"));
		myNoteDescription.click();
		myNoteDescription.sendKeys("Description1");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-change")));
		WebElement btnSaveNote = driver.findElement(By.id("save-note-change"));
		btnSaveNote.click();
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement btnTabNote1 = driver.findElement(By.id("nav-notes-tab"));
		btnTabNote1.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-edit-note")));
		WebElement btnShowNoteModal1 = driver.findElement(By.id("btn-edit-note"));
		btnShowNoteModal1.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitleField = driver.findElement(By.id("note-title"));
		noteTitleField.click();
		noteTitleField.clear();
		noteTitleField.sendKeys("TitleEdit");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-change")));
		WebElement btnSaveNote1 = driver.findElement(By.id("save-note-change"));
		btnSaveNote1.click();
		Assertions.assertTrue(driver.getPageSource().contains("TitleEdit"));
	}

	@Test
	public void testDeleteNote() {
		doMockSignUp("DeleteNote","Test","DeleteNote","123");
		doLogIn("DeleteNote", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement btnTabNote = driver.findElement(By.id("nav-notes-tab"));
		btnTabNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-note-modal")));
		WebElement btnShowNoteModal = driver.findElement(By.id("btn-show-note-modal"));
		btnShowNoteModal.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement myNoteTitle = driver.findElement(By.id("note-title"));
		myNoteTitle.click();
		myNoteTitle.sendKeys("TitleDelete2");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement myNoteDescription = driver.findElement(By.id("note-description"));
		myNoteDescription.click();
		myNoteDescription.sendKeys("Description1");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-change")));
		WebElement btnSaveNote = driver.findElement(By.id("save-note-change"));
		btnSaveNote.click();
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement btnTabNote1 = driver.findElement(By.id("nav-notes-tab"));
		btnTabNote1.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-delete-note")));
		WebElement btnShowNoteModal1 = driver.findElement(By.id("btn-delete-note"));
		btnShowNoteModal1.click();
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		WebElement btnTabNote2 = driver.findElement(By.id("nav-notes-tab"));
		btnTabNote2.click();
		Assertions.assertFalse(driver.getPageSource().contains("TitleDelete2"));
	}


	@Test
	public void testAddingCredential() {
		doMockSignUp("AddCre","Test","AddCre2","123");
		doLogIn("AddCre2", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement btnTabCredential = driver.findElement(By.id("nav-credentials-tab"));
		btnTabCredential.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-credential-modal")));
		WebElement btnShowCredentialModal = driver.findElement(By.id("btn-show-credential-modal"));
		btnShowCredentialModal.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement myUrl = driver.findElement(By.id("credential-url"));
		myUrl.click();
		myUrl.sendKeys("http://localhost:4200/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-userName")));
		WebElement myUserName = driver.findElement(By.id("credential-userName"));
		myUserName.click();
		myUserName.sendKeys("username");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement myPassword = driver.findElement(By.id("credential-password"));
		myPassword.click();
		myPassword.sendKeys("password");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-save-cre-change")));
		WebElement btnSaveNote = driver.findElement(By.id("btn-save-cre-change"));
		btnSaveNote.click();
		Assertions.assertTrue(driver.getPageSource().contains("username"));
	}
	@Test
	public void testEditCredential() {
		doMockSignUp("EditCre","Test","EditCre3","123");
		doLogIn("EditCre3", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement btnTabCredential = driver.findElement(By.id("nav-credentials-tab"));
		btnTabCredential.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-credential-modal")));
		WebElement btnShowCredentialModal = driver.findElement(By.id("btn-show-credential-modal"));
		btnShowCredentialModal.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement myUrl = driver.findElement(By.id("credential-url"));
		myUrl.click();
		myUrl.sendKeys("http://localhost:4200/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-userName")));
		WebElement myUserName = driver.findElement(By.id("credential-userName"));
		myUserName.click();
		myUserName.sendKeys("username");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement myPassword = driver.findElement(By.id("credential-password"));
		myPassword.click();
		myPassword.sendKeys("password");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-save-cre-change")));
		WebElement btnSaveNote = driver.findElement(By.id("btn-save-cre-change"));
		btnSaveNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement btnTabCredential1 = driver.findElement(By.id("nav-credentials-tab"));
		btnTabCredential1.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-edit-credential")));
		WebElement btnShowCredentialModal1 = driver.findElement(By.id("btn-edit-credential"));
		btnShowCredentialModal1.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-userName")));
		WebElement creUsernameField = driver.findElement(By.id("credential-userName"));
		creUsernameField.click();
		creUsernameField.clear();
		creUsernameField.sendKeys("usernameEdit");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-save-cre-change")));
		WebElement btnSaveCredential = driver.findElement(By.id("btn-save-cre-change"));
		btnSaveCredential.click();
		Assertions.assertTrue(driver.getPageSource().contains("usernameEdit"));
	}
	@Test
	public void testDeleteCredential() {
		doMockSignUp("DeleteCre","Test","DeleteCre","123");
		doLogIn("DeleteCre", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement btnTabCredential = driver.findElement(By.id("nav-credentials-tab"));
		btnTabCredential.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-credential-modal")));
		WebElement btnShowCredentialModal = driver.findElement(By.id("btn-show-credential-modal"));
		btnShowCredentialModal.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement myUrl = driver.findElement(By.id("credential-url"));
		myUrl.click();
		myUrl.sendKeys("http://localhost:4200/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-userName")));
		WebElement myUserName = driver.findElement(By.id("credential-userName"));
		myUserName.click();
		myUserName.sendKeys("username");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement myPassword = driver.findElement(By.id("credential-password"));
		myPassword.click();
		myPassword.sendKeys("password");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-save-cre-change")));
		WebElement btnSaveNote = driver.findElement(By.id("btn-save-cre-change"));
		btnSaveNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement btnTabCredential1 = driver.findElement(By.id("nav-credentials-tab"));
		btnTabCredential1.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-delete-credential")));
		WebElement btnSaveNote1 = driver.findElement(By.id("btn-delete-credential"));
		btnSaveNote1.click();
		Assertions.assertFalse(driver.getPageSource().contains("user1"));
	}


}
