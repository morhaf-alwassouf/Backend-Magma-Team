package com.magma.main.Utils;

import com.magma.main.Models.AdImages;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.bson.Document;

public class EXPORT_EXCEL {


    private static String[] columns = { "#", "Ad Code", "Image Index","Image Url"};

    public EXPORT_EXCEL(){}

    public static String export_(ArrayList<Document> images) throws IOException {

        String ProjectPath = new GeneralFunctions().getProjectPath();
        File dir = new File(ProjectPath+File.separator+"resources"+File.separator+"ExcelFiles");

        if(dir.exists()){
            if(!dir.isDirectory()){
                if(!new File(ProjectPath+File.separator+"resources"+File.separator+"ExcelFiles").mkdirs()){
                    return "";
                }
            }
        }else{
            if(!new File(ProjectPath+File.separator+"resources"+File.separator+"ExcelFiles").mkdirs()){
                return "";
            }
        }



        String fileName = dir.toPath().toString()+File.separator+"Rejected_images_lst_.xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Reviews");

        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillBackgroundColor((short) 12);
        style.setBorderTop(BorderStyle.DOUBLE); // double lines border
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 15);
        font.setBold(true);
        style.setFont(font);

        writeHeaderLine(sheet,style);

        writeDataLines(images, workbook, sheet);

        FileOutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        workbook.close();

        return fileName;


    }



    private static void writeHeaderLine(XSSFSheet sheet,XSSFCellStyle style) {

        Row headerRow = sheet.createRow(0);

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("#");
        headerCell.setCellStyle(style);
        sheet.autoSizeColumn(0);

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Ad Code");
        headerCell.setCellStyle(style);
        sheet.autoSizeColumn(0);

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Image Index");
        headerCell.setCellStyle(style);
        sheet.autoSizeColumn(1);

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Image URL");
        headerCell.setCellStyle(style);
        sheet.autoSizeColumn(2);

    }


    private static void writeDataLines(ArrayList<Document> data, XSSFWorkbook workbook,
                                       XSSFSheet sheet)  {
        int rowCount = 1;

        for (Document doc : data) {
            String url = "",index = "";

            int adCode = doc.getInteger("adCode");

            if(doc.get("imageURL") != null){
                url = doc.getString("imageURL");
            }

            if(doc.get("imageIndex") != null){
                index = doc.get("imageIndex").toString();
            }

            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(rowCount -1);


            cell = row.createCell(columnCount++);
            cell.setCellValue(adCode);



            cell = row.createCell(columnCount++);
            cell.setCellValue(index);

            cell = row.createCell(columnCount++);
            cell.setCellValue(url);


        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

    }

}
