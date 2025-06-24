package com.pgEnterprise.pgCoding.ToolsDemo.WindowHandle;

import TestBase.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class WIndowHandleTest extends TestBase {



    WebDriver driver;
    @Test
    public void windowHandleTest() throws IOException {

       init();

        driver=  launchBrowser(Param.getProperty("browser"),false);

        driver.get("https://testautomationpractice.blogspot.com/");
        driver.manage().window().maximize();

        // 2. Enter search term and click Search
        driver.findElement(By.id("Wikipedia1_wikipedia-search-input")).sendKeys("Selenium");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // 3. Wait for search result links to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='wikipedia-search-result-link']/a")));

        // 4. Get the list of links
        List<WebElement> links = driver.findElements(By.xpath("//div[@id='wikipedia-search-result-link']/a"));
        System.out.println("Number of search result links: " + links.size());

        // 5. Loop through each link
        for (int i = 0; i < links.size(); i++) {
            String mainWindow = driver.getWindowHandle();

            // Refetch the links due to DOM refresh possibility
            List<WebElement> currentLinks = driver.findElements(By.xpath("//div[@id='wikipedia-search-result-link']/a"));
            currentLinks.get(i).click();

            // 6. Wait until a new window opens
            wait.until(driver1 -> driver1.getWindowHandles().size() > 1);

            // 7. Get all window handles and switch
            Set<String> allWindows = driver.getWindowHandles();

            for (String window : allWindows) {
                if (!window.equals(mainWindow)) {
                    driver.switchTo().window(window);

                    // 8. Wait for title to load
                    wait.until(ExpectedConditions.titleIs(driver.getTitle()));
                    String title = driver.getTitle();
                    System.out.println("Title: " + title);
                    System.out.println("Window ID: " + window);

                    // 9. Close the specific window if title matches
                    if (title.equals("Selenium (software) - Wikipedia")) {
                        System.out.println("Closing window with title: " + title);
                        driver.close();
                    } else {
                        //Optional: keep other windows open
                        driver.switchTo().window(mainWindow);
                    }
                }
            }

            // 10. Switch back to main window
            driver.switchTo().window(mainWindow);
        }
    }
}
