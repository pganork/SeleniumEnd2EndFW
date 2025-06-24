package Utility;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class ExcelDataProvider {

    private static final String EXCEL_PATH = System.getProperty("user.dir")+"/src/main/java/ExcelFolder/TestData.xlsx";

    @DataProvider(name = "excelData")
    public static Object[][] getData(Method method) throws IOException {
        String sheetName = method.getDeclaringClass().getSimpleName();  // Class name as sheet name
        return fetchDataFromExcel(sheetName);
    }

    private static Object[][] fetchDataFromExcel(String sheetName) throws IOException {
        List<Map<String, String>> dataList = new ArrayList<>();

        FileInputStream fis = new FileInputStream(EXCEL_PATH);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            workbook.close();
            throw new RuntimeException("Sheet '" + sheetName + "' not found in Excel file.");
        }

        Row headerRow = sheet.getRow(0);
        int totalCols = headerRow.getLastCellNum();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String runFlag = getCellValue(row.getCell(getColumnIndex(headerRow, "Run")));
            if (!"Y".equalsIgnoreCase(runFlag)) continue;

            Map<String, String> dataMap = new HashMap<>();
            for (int j = 0; j < totalCols; j++) {
                String key = getCellValue(headerRow.getCell(j));
                String value = getCellValue(row.getCell(j));
                dataMap.put(key, value);
            }
            dataList.add(dataMap);
        }

        workbook.close();
        return convertListToArray(dataList);
    }

    private static int getColumnIndex(Row headerRow, String columnName) {
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            if (columnName.equalsIgnoreCase(getCellValue(headerRow.getCell(i)))) {
                return i;
            }
        }
        throw new RuntimeException("Column '" + columnName + "' not found in sheet.");
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

    private static Object[][] convertListToArray(List<Map<String, String>> dataList) {
        Object[][] array = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            array[i][0] = dataList.get(i); // Send as Map<String, String>
        }
        return array;
    }
}

