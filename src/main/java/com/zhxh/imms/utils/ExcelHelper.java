package com.zhxh.imms.utils;

import com.zhxh.imms.data.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ExcelHelper {
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    public static void readExcel(MultipartFile file, int firstRow, int lastRow, CallBackMethod rowReadMethod) {
        checkFile(file);
        Workbook workbook = getWorkBook(file);
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                for (int rowNum = firstRow; rowNum <= lastRow; rowNum++) {
                    rowReadMethod.execute(sheet.getRow(rowNum));
                }
            }
        }
    }

    public static void checkFile(MultipartFile file) {
        if (null == file) {
            throw new BusinessException(new FileNotFoundException("文件不存在！"));
        }
        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
            throw new BusinessException(new IOException(fileName + "不是excel文件"));
        }
    }

    public static Workbook getWorkBook(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            InputStream is = file.getInputStream();
            if (fileName.endsWith(xls)) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(xlsx)) {
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            Logger.info(e.getMessage());
        }
        return workbook;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING: //字符串
            case FORMULA: //公式
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
//            case FORMULA: //公式
////                cellValue = String.valueOf(cell.getCellFormula());
//                cellValue = String.valueOf(cell.getStringCellValue());
//                break;
            case BLANK: //空值
                cellValue = "";
                break;
            case ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}