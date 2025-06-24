package com.pgEnterprise.pgCoding.ToolsDemo.ToolDemoPractice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MoneyControl {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set up the ChromeDriver path
        //  System.setProperty("webdriver.chrome.driver", "path_to_chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.moneycontrol.com/");
    }

    @Test
    public void find52WeekHigh() throws InterruptedException {
        // Locate the search bar and search for "Reliance"
        Thread.sleep(10000);
        WebElement searchBox = driver.findElement(By.id("search_str")); // Adjust locator if needed
        searchBox.sendKeys("Reliance");

        WebElement searchButton = driver.findElement(By.id("searchbtn")); // Adjust locator if needed
        searchButton.click();

        // Click on the first result for Reliance
        WebElement firstResult = driver.findElement(By.xpath("(//div[@class='FL gry10'])[1]"));
        firstResult.click();

        // Wait for stock details page to load and fetch the 52-week high value
        WebElement high52Week = driver.findElement(By.xpath("//div[contains(text(),'52 Week High')]/following-sibling::div"));
        String highValue = high52Week.getText();

        System.out.println("The 52-week high value for Reliance is: " + highValue);
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
