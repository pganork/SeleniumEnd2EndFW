package Utility;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.commons.io.FileUtils;
public class PageFunctions {



    public static void extentScreenShot(ExtentTest test, WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        String destPath = "test-output/screenshots/pageConfirm.png";  // Adjust path as needed
        try {
            FileUtils.copyFile(src, new File(destPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //test.addScreenCaptureFromPath("", "Screenshot");
        test.pass(MediaEntityBuilder.createScreenCaptureFromPath("screenshots/pageConfirm.png").build());

    }
    public static void takeScreenshot(WebDriver driver, String filePath) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(filePath));
            System.out.println("Screenshot saved at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 2. Scroll down to element
    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // 3. JavaScript Click
    public static void jsClick(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // 4. Wait until visibility
    public static WebElement waitForVisibility(WebDriver driver, WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    // 5. Switch to Frame by WebElement
    public static void switchToFrame(WebDriver driver, WebElement frameElement) {
        driver.switchTo().frame(frameElement);
    }

    // 6. Switch to default content
    public static void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    // 7. Switch to window by title validation
    public static void switchToWindowByTitle(WebDriver driver, String expectedTitle) {
        String currentWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();

        for (String window : windows) {
            driver.switchTo().window(window);
            if (driver.getTitle().equalsIgnoreCase(expectedTitle)) {
                return;
            }
        }
        // If not found, switch back
        driver.switchTo().window(currentWindow);
        System.out.println("Window with title '" + expectedTitle + "' not found.");
    }

    // 8. Click on button safely
    public static void clickButton(WebDriver driver, WebElement button) {
        waitForVisibility(driver, button, 10).click();
    }

    // 9. Upload file (input type='file')
    public static void uploadFile(WebElement fileInputElement, String filePath) {
        fileInputElement.sendKeys(filePath);
    }

    // 10. Scroll by pixels
    public static void scrollByPixels(WebDriver driver, int x, int y) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(arguments[0], arguments[1])", x, y);
    }
}
