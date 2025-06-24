package com.pgEnterprise.pgCoding.ToolsDemo.CDP;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;

import javax.annotation.concurrent.Immutable;
import java.util.Optional;

public class BlockingUnwantedCSS {

    public static void main(String[] args) {
        ChromeDriver driver = new ChromeDriver();

        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(),Optional.empty(),
                Optional.empty()));

        devTools.send(Network.setBlockedURLs(ImmutableList.of("*.jpg","*.css")));

        driver.get("https://www.phoenixinsurance.in/");
    }
}
