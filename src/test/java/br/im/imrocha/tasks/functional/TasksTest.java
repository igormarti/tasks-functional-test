package br.im.imrocha.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

	
	public WebDriver initTest() throws MalformedURLException {
//		WebDriver driver = new ChromeDriver();
		WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), new ChromeOptions());
		// open page tasks list
		driver.navigate().to("http://192.168.0.34:8081/tasks/");
		return driver;
	}
	
	@Test
	public void shouldSaveTaskWithSuccess() throws MalformedURLException {
		WebDriver driver = initTest();
		
		try {
			// wait 3 seconds
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			// click button add task
			driver.findElement(By.id("addTodo")).click();
			// fill fields
			driver.findElement(By.id("task")).sendKeys("task test");
			driver.findElement(By.id("dueDate")).sendKeys(LocalDate.now().plusWeeks(3).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			// click button save
			driver.findElement(By.id("saveButton")).click();
			// show success message
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
		} finally {
			// close browser
			driver.quit();
		}
	}
	
	@Test
	public void shouldNotSaveTaskWithOutDescription() throws MalformedURLException {
		
		WebDriver driver = initTest();
		
		try {
			// wait 3 seconds
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			// click button add task
			driver.findElement(By.id("addTodo")).click();
			// fill due date field
			driver.findElement(By.id("dueDate")).sendKeys(LocalDate.now().plusWeeks(3).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			// click button save
			driver.findElement(By.id("saveButton")).click();
			// show success message
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
		} finally {
			// close browser
			driver.quit();
		}	
	}
	
	@Test
	public void shouldNotSaveTaskWithOutDueDate() throws MalformedURLException {
		
		WebDriver driver = initTest();
		
		try {
			// wait 3 seconds
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			// click button add task
			driver.findElement(By.id("addTodo")).click();
			// fill description field
			driver.findElement(By.id("task")).sendKeys("test");
			// click button save
			driver.findElement(By.id("saveButton")).click();
			// show success message
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);
		} finally {
			// close browser
			driver.quit();
		}	
	}
	
	@Test
	public void shouldNotSaveTaskWithPastDate() throws MalformedURLException {
		
		WebDriver driver = initTest();
		
		try {
			// wait 3 seconds
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			// click button add task
			driver.findElement(By.id("addTodo")).click();
			// fill description field
			driver.findElement(By.id("task")).sendKeys("test");
			// fill due date field with past date
			driver.findElement(By.id("dueDate")).sendKeys("03/10/1996");
			// click button save
			driver.findElement(By.id("saveButton")).click();
			// show success message
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		} finally {
			// close browser
			driver.quit();
		}	
	}
	
}
