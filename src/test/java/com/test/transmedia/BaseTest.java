package com.test.transmedia;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BaseTest {
	private WebDriver driver;
	private String url;
	private String boardName = "Project Management";
	private String listName1 = "To Do";
	private String listName2 = "In Progress";

	@BeforeTest
	public void setUp() {
		driver = new ChromeDriver();
		url = "http://localhost:3000/";
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	@Test
	public void testCreateBoardAndLists() {

//		Navigate to the web application
		driver.get(url);

//		Create Board
		driver.findElement(By.className("create-board")).click();
		WebElement boardInputElement = driver.findElement(By.className("new-board-input"));
		boardInputElement.sendKeys(boardName);
		boardInputElement.sendKeys(Keys.ENTER);

//		Validate the board is created
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement boardElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-cy='board-title']")));
		Assert.assertTrue(boardElement.isDisplayed(), "Board was not created successfully");
		
//		print board created message as output
		System.out.println("Board Created Successfully.");

//		Create First List
		WebElement addListInput1 = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-cy='add-list-input']")));
		addListInput1.sendKeys(listName1);
		driver.findElement(By.xpath("//button[normalize-space()='Add list']")).click();

//      Wait for the first list to be added
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-cy='list-name']")));
	

//		Create Second List
		WebElement addListInput2 = driver.findElement(By.cssSelector("[data-cy='add-list-input']"));
		addListInput2.clear();
		addListInput2.sendKeys(listName2);
		driver.findElement(By.xpath("//button[normalize-space()='Add list']")).click();

//      Wait for the first list to be added
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-cy='list-name']")));
		
//		Verify the list are created successfully
		int expectedListCount = 2;
		List<WebElement> lists = driver.findElements(By.cssSelector("[data-cy='list']"));
		int actualListCount = lists.size();
		Assert.assertEquals(actualListCount, expectedListCount, "The number of lists created is incorrect");

//		print success message for added elements
		System.out.println("First and Second Lists Added Successfully.");
		
//		Delete a list
		WebElement listOptions = driver.findElement(By.xpath("(//*[@data-cy='list-options'])[2]"));
		listOptions.click();

		WebElement deleteListButton = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-cy='delete-list']")));
		deleteListButton.click();

//		Wait for the list to be deleted
		wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("[data-cy='list']"), expectedListCount - 1));
		
//		print delete the element successfully
		System.out.println("Second List Deleted Successfully.");
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
