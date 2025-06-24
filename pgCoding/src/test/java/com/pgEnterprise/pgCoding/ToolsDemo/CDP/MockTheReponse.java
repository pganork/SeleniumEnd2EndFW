package com.pgEnterprise.pgCoding.ToolsDemo.CDP;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v130.emulation.Emulation;
import org.openqa.selenium.devtools.v85.fetch.Fetch;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;

public class MockTheReponse {

    public static void main(String[] args) {

        ChromeDriver driver = new ChromeDriver();

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Fetch.enable(Optional.empty(),Optional.empty()));
        devTools.addListener(Fetch.requestPaused(), requestPaused -> {

            if(requestPaused.getRequest().getUrl().contains("=shetty"))
            {

                String newUrl = requestPaused.getRequest().getUrl().replace("=shetty","=BadGuy");

                System.out.println("New Url " + newUrl);

                devTools.send(Fetch.continueRequest(requestPaused.getRequestId(),Optional.of(newUrl),Optional.of(requestPaused.getRequest().getMethod()),
                        requestPaused.getRequest().getPostData(),requestPaused.getResponseHeaders()));
            }
            else{

                devTools.send(Fetch.continueRequest(requestPaused.getRequestId(),Optional.of(requestPaused.getRequest().getUrl()),Optional.of(requestPaused.getRequest().getMethod()),
                        requestPaused.getRequest().getPostData(),requestPaused.getResponseHeaders()));

            }


        });

        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.cssSelector("button[routerlink*='library']")).click();

    }


}
