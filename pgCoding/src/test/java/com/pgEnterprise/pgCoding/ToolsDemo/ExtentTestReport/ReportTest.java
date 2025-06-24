package com.pgEnterprise.pgCoding.ToolsDemo.ExtentTestReport;

import TestBase.TestBase;
import Utility.PageFunctions;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static TestBase.TestBase.extent;

public class ReportTest extends TestBase {


    WebDriver driver;

    @BeforeMethod
    public void beforeTest() {
        ReportTest obj = new ReportTest();
        String className = obj.getClass().getName();

        test = extent.createTest(className);
    }

    @Test
    public void checkExtentReporting() throws IOException, InterruptedException {


        init();
        driver = launchBrowser(Param.getProperty("browser"),false);

        driver.get("https://testautomationpractice.blogspot.com/");
        driver.manage().window().maximize();

        test.info("Page Opened");


        //Scroll to the table
        WebElement table = driver.findElement(By.xpath("//h2[normalize-space()='Pagination Web Table']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", table);

        // PageFunctions.extentScreenShot(test,driver);

        // Locate the pagination container
        List<WebElement> pages = driver.findElements(By.xpath("//ul[@class='pagination']/li"));
        int totalPages = pages.size();
        System.out.println("Total Pages: " + totalPages);
        // Loop through each page
        for (int i = 1; i <= totalPages; i++) {
            // Click the pagination link
            WebElement pageLink = driver.findElement(By.xpath("//ul[@class='pagination']/li[" + i + "]/a"));
            pageLink.click();

            // Wait for the table to update (simple static wait for demonstration)
            Thread.sleep(2000);

            // Select all checkboxes in the table
            List<WebElement> checkboxes = driver.findElements(By.xpath("//table[@id='productTable']//input[@type='checkbox']"));
            for (WebElement checkbox : checkboxes) {
                if (!checkbox.isSelected()) {
                    checkbox.click();
                }
                System.out.println("Checkbox selected: " + checkbox.isSelected());
            }


            PageFunctions.extentScreenShot(test,driver);
        }
        driver.close();
    }


    }

