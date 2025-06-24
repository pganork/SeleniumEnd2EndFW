package com.pgEnterprise.pgCoding.ToolsDemo.ToolDemoPractice;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Set;

public class ActionClassTest {
  @Test
  public void ActionTest() throws InterruptedException, AWTException {

    ChromeDriver driver = new ChromeDriver();

    driver.get("https://rahulshettyacademy.com/AutomationPractice/");
    driver.manage().window().maximize();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("dropdown-class-example"))));

    Select s = new Select(driver.findElement(By.id("dropdown-class-example")));

    System.out.println("Is Multiple " + s.isMultiple());
    s.selectByVisibleText("Option1");

    // ************ Suggestive Drop Down ************************//


   WebElement suggestDrop =  driver.findElement(By.xpath("//input[@placeholder ='Type to Select Countries']"));
//    Actions actions = new Actions(driver);
//
//    actions.moveToElement(suggestDrop).click().sendKeys("br").click().keyDown(Keys.ARROW_DOWN).keyUp(Keys.ARROW_DOWN).keyDown(Keys.ARROW_DOWN).keyUp(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();

    suggestDrop.sendKeys("Br");
    suggestDrop.click();

    Robot robot = new Robot();
    Thread.sleep(500); // Small delay to ensure dropdown is active

    // Press ARROW_DOWN twice
    robot.keyPress(KeyEvent.VK_DOWN);
    robot.keyRelease(KeyEvent.VK_DOWN);
    Thread.sleep(300); // Delay between key presses
    robot.keyPress(KeyEvent.VK_DOWN);
    robot.keyRelease(KeyEvent.VK_DOWN);

    // Press ENTER
    Thread.sleep(300); // Delay before pressing Enter
    robot.keyPress(KeyEvent.VK_ENTER);
    robot.keyRelease(KeyEvent.VK_ENTER);

    //****************** Is Display *******************************//

    driver.findElement(By.xpath("//input[@value='Show']")).click();

    WebElement hiddenELe = driver.findElement(By.xpath("//input[@id='displayed-text']"));

    System.out.println("Hidden Element " + hiddenELe.getAttribute("value"));
    Assert.assertTrue(hiddenELe.isDisplayed());

    driver.findElement(By.xpath("//input[@value='Hide']")).click();

    //Assert.assertTrue(hiddenELe.isDisplayed());


    // *************** Switch Window ****************************** //

    driver.findElement(By.id("openwindow")).click();

    Set<String> window = driver.getWindowHandles();

    String mainWindow = driver.getWindowHandle();

    for(String handle : window){

      driver.switchTo().window(handle);
      if(!driver.getWindowHandle().equals(mainWindow)){
        break;
      }

    }
    wait.until(ExpectedConditions.visibilityOf( driver.findElement(By.xpath("//a[text()='Access all our Courses']"))));

    driver.findElement(By.xpath("//a[text()='Access all our Courses']")).click();






  }
}
