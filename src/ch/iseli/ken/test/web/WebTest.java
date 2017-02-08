package ch.iseli.ken.test.web;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebTest {
	// TODO: Adjust this value according to your OS. See package ch.iseli.ken.test.web
	private static final String CHROME_DRIVER_RESOURCE_PATH = "ch/iseli/ken/test/web/chromedriver_win32.exe";

	private static final String WIKIPEDIA_USERNAME = "kennethIzeli";
	private static final String WIKIPEDIA_PASSWORD = "kennethIseli";

	private WebDriver driver;

	@Before
	public void setUp() throws URISyntaxException {
		ChromeOptions options = new ChromeOptions();
		URL resource = getClass().getClassLoader().getResource(CHROME_DRIVER_RESOURCE_PATH);
		System.setProperty("webdriver.chrome.driver", Paths.get(resource.toURI()).toString());
		options.addArguments("start-maximized");
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(caps);
	}

	@Test
	public void testLoginToEnglishWikipedia() {
		// Launch the Online Store Website
		driver.get("http://www.wikipedia.org");

		By wikipediaEnglishId = By.id("js-link-box-en");
		WebElement englishWikipediaLink = driver.findElement(wikipediaEnglishId);
		Assert.assertTrue(englishWikipediaLink.isDisplayed());
		Assert.assertTrue(englishWikipediaLink.isEnabled());

		// Let's go with english
		englishWikipediaLink.click();
		Assert.assertTrue(driver.getCurrentUrl().contains("en.wikipedia.org"));

		// Login
		By loginLinkText = By.linkText("Log in");
		WebElement loginLink = driver.findElement(loginLinkText);
		Assert.assertTrue(loginLink.isDisplayed());
		loginLink.click();

		By usernameInputId = By.id("wpName1");
		WebElement usernameInputField = driver.findElement(usernameInputId);
		usernameInputField.sendKeys(WIKIPEDIA_USERNAME);

		By passwordInputId = By.id("wpPassword1");
		WebElement passwordInputField = driver.findElement(passwordInputId);
		passwordInputField.sendKeys(WIKIPEDIA_PASSWORD);

		passwordInputField.submit();

		// Exactly three elements with class "new" in the header are to be present
		By classNameNew = By.className("new");
		List<WebElement> headerWithClassNewElements = driver.findElements(classNameNew);
		Assert.assertEquals(3, headerWithClassNewElements.size());
		for (WebElement headerWithClassNewElement : headerWithClassNewElements) {
			Assert.assertTrue(headerWithClassNewElement.isDisplayed());
			Assert.assertTrue(headerWithClassNewElement.isEnabled());
		}

		// Close the driver
		driver.quit();

	}

}
