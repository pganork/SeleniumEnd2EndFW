package com.pgEnterprise.pgCoding.ToolsDemo.ExcelUtilityDemo;

import org.testng.annotations.Test;

import java.util.Map;

public class DataDrivenTest {

    @Test(dataProvider = "excelData", dataProviderClass = Utility.ExcelDataProvider.class)
    public void testLogin(Map<String, String> data) {
        System.out.println("Running test with data: " + data);

        String username = data.get("Username");
        String password = data.get("Password");

        System.out.println("Username " + username);
        System.out.println("Password " + password);


    }
}
