package com.manibala.framework.data;

import com.manibala.application.groq.api.config.ConfigProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestData {

    public static List<String> readExcel(String sheetName, String tcId) {
        List<String> data = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(ConfigProperties.getTestDataPath());
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            int lastRowNum = sheet.getLastRowNum();
            for (int i=1; i<=lastRowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                String tc = row.getCell(0).getStringCellValue();
                if (tc.equals(tcId)) {
                    for (int j=1; j<row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        String cellValue = cell==null ? "" : cell.getStringCellValue();
                        data.add(cellValue);
                    }
                }
            }
            workbook.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
