package com.pgEnterprise.pgCoding.ToolsDemo.ToolDemoPractice;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class Resizable {

    @Test
    public void resizable() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/sortable");
        driver.manage().window().maximize();

        JavascriptExecutor js = (JavascriptExecutor) driver;


        WebElement resizable = driver.findElement(By.xpath("//*[text()='Resizable']/parent::li"));

        js.executeScript("arguments[0].click();",resizable);
        WebElement resize = driver.findElement(By.xpath("//*[@class='react-resizable-handle react-resizable-handle-se']"));


        js.executeScript("arguments[0].scrollIntoView(true);", resize);

        Point point = resize.getLocation();

        Actions action = new Actions(driver);
        Thread.sleep(3000);

        action.clickAndHold(resize).moveByOffset(150,150).build().perform();
//        action.dragAndDropBy(resize,50,50).build().perform();



    }
}
