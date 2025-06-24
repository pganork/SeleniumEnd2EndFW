package com.pgEnterprise.pgCoding.ToolsDemo.ToolDemoPractice;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class Drawable {

    @Test
    public void DragMethods(){

        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/sortable");
        driver.manage().window().maximize();

        WebElement listView = driver.findElement(By.id("demo-tab-grid"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", listView);
        listView.click();

        Actions action = new Actions(driver);

        WebElement one = driver.findElement(By.xpath("//div[@aria-labelledby = 'demo-tab-grid']//*[text()='One']"));

        WebElement two = driver.findElement(By.xpath("//div[@aria-labelledby = 'demo-tab-grid']//*[text()='Nine']"));

        Point point = two.getLocation();
        Point point2 = one.getLocation();


        int x =point.getX()-point2.getX();
        int y =point.getY()-point2.getY();

//        action.dragAndDrop(one,two).build().perform();
        action.dragAndDropBy(one,x,y).build().perform();



    }
}
