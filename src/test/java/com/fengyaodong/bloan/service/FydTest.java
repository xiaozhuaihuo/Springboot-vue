package com.fengyaodong.bloan.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

/**
 * 测试
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/6/17 21:17
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/6/17 21:17
 */
@Slf4j
public class FydTest {

    public void downloadFile(String filePath) throws Exception{

        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        InputStream inputStream = new FileInputStream(file);

        int fileLength = (int)file.length();
        byte[] bs = new byte[fileLength];
        inputStream.read(bs);
        inputStream.close();
    }

    public void uploadFile() throws Exception {

        String filePath = "E:\\test1.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        String str = "HELLO WORLD";
        byte[] bs = str.getBytes();
        outputStream.write(bs);
        outputStream.close();
    }

    public void downloadExcel(String filePath) throws Exception {

        File file = new File(filePath);
        if (!file.exists()) {
            throw new  Exception("源文件不存在");
        }

        File file1 = new File("E:\\test4.xlsx");
        if (!file.exists()) {
            file.createNewFile();
        }

        InputStream inputStream = new FileInputStream(file);

        byte[] bs = new byte[(int)file.length()];
        inputStream.read(bs);
        OutputStream outputStream = new FileOutputStream(file1);
        outputStream.write(bs);
        outputStream.close();
        inputStream.close();
    }

    public void upLoadExcel() throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("第一个表");
        XSSFCellStyle cellStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setColor(HSSFColor.RED.index);
        cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_YELLOW.index);
        cellStyle.setFont(font);
        for (int i = 0; i <= 20; i++) {
            sheet.createRow(i);
            for (int j = 0; j <= 20; j++) {
                sheet.getRow(i).createCell(j).setCellValue("我是第" + (i + 1) + "行第" + (j + 1) + "列数据");
                if (i == j) {
                    sheet.getRow(i).getCell(j).setCellStyle(cellStyle);
                }
            }
        }

        File file = new File("E:\\fyd.xlsx");
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        wb.write(outputStream);
        outputStream.close();
    }


    public static void main(String[] args) throws Exception{
        FydTest test = new FydTest();
        test.upLoadExcel();
    }
}
