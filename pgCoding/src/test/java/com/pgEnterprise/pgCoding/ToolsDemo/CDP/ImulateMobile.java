package com.pgEnterprise.pgCoding.ToolsDemo.CDP;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v130.emulation.Emulation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

public class ImulateMobile {

    public static void main(String[] args) {
        System.out.println("Hii");

        ChromeDriver driver = new ChromeDriver();

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Emulation.setDeviceMetricsOverride(500, 1000, 50, true, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("navbar-toggler-icon"))));
        driver.findElement(By.className("navbar-toggler-icon")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@routerlink='/products']"))));
        driver.findElement(By.xpath("//a[@routerlink='/products']"));
    }
}
