package br.im.imrocha.tasks.prod;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HealthCheckIT {

	private Boolean appDeployed;


	public WebDriver initTest() throws MalformedURLException {
		WebDriver driver = new ChromeDriver();
		// this line is for use in real scenery with hub and nodes.
		// ImmutableCapabilities capabilities = new ImmutableCapabilities("browserName", "chrome");
		// WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"),capabilities);
		// open page tasks list
		driver.navigate().to("http://192.168.0.34:9999/tasks/");
		return driver;
	}
	
	
	@Test
	public void healthCheck() throws MalformedURLException {
		WebDriver driver = initTest();
		try {
			// wait 3 seconds
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// check if application is run to the get version in the element
			appDeployed = driver.findElement(By.id("version")).isDisplayed();
			// test if the element exists
			Assert.assertTrue(appDeployed);
		} finally {
			// close browser
			driver.quit();
		}
	}
}
