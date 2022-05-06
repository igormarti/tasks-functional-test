package br.im.imrocha.tasks.functional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {

	
	public WebDriver initTest() {
		WebDriver driver = new ChromeDriver();
		// open page tasks list
		driver.navigate().to("http://localhost:8081/tasks/");
		// wait 3 seconds
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		return driver;
	}
	
	@Test
	public void shouldSaveTaskWithSuccess() {
		WebDriver driver = new ChromeDriver();
		try {
			// open page tasks list
			driver.navigate().to("http://localhost:8081/tasks/");
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
			var message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
		} finally {
			// close browser
			driver.quit();
		}
	}
	
	@Test
	public void shouldNotSaveTaskWithOutDescription() {
		
		WebDriver driver = initTest();
		
		try {
			// click button add task
			driver.findElement(By.id("addTodo")).click();
			// fill due date field
			driver.findElement(By.id("dueDate")).sendKeys(LocalDate.now().plusWeeks(3).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			// click button save
			driver.findElement(By.id("saveButton")).click();
			// show success message
			var message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
		} finally {
			// close browser
			driver.quit();
		}	
	}
	
	@Test
	public void shouldNotSaveTaskWithOutDueDate() {
		
		WebDriver driver = initTest();
		
		try {
			// click button add task
			driver.findElement(By.id("addTodo")).click();
			// fill description field
			driver.findElement(By.id("task")).sendKeys("test");
			// click button save
			driver.findElement(By.id("saveButton")).click();
			// show success message
			var message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);
		} finally {
			// close browser
			driver.quit();
		}	
	}
	
	@Test
	public void shouldNotSaveTaskWithPastDate() {
		
		WebDriver driver = initTest();
		
		try {
			// click button add task
			driver.findElement(By.id("addTodo")).click();
			// fill description field
			driver.findElement(By.id("task")).sendKeys("test");
			// fill due date field with past date
			driver.findElement(By.id("dueDate")).sendKeys("03/10/1996");
			// click button save
			driver.findElement(By.id("saveButton")).click();
			// show success message
			var message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		} finally {
			// close browser
			driver.quit();
		}	
	}
	
}
