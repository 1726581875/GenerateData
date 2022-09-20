package zhangyu.fool.generate.service.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author xiaomingzhang
 * @date 2022/3/29
 */
public class Exporter {

    private static final Logger log = LoggerFactory.getLogger(Exporter.class);

    public static void main(String[] args) throws Exception {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(2);
        row.setHeightInPoints(30);
        createCell(wb, row, 0, HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);
        createCell(wb, row, 1, HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.BOTTOM);
        createCell(wb, row, 2, HorizontalAlignment.FILL, VerticalAlignment.CENTER);
        createCell(wb, row, 3, HorizontalAlignment.GENERAL, VerticalAlignment.CENTER);
        createCell(wb, row, 4, HorizontalAlignment.JUSTIFY, VerticalAlignment.JUSTIFY);
        createCell(wb, row, 5, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
        createCell(wb, row, 6, HorizontalAlignment.RIGHT, VerticalAlignment.TOP);
        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("C:\\Users\\xmz\\Desktop\\test\\logs\\xssf-align.xlsx")) {
            wb.write(fileOut);
        }
        wb.close();
    }
    /**
     * Creates a cell and aligns it a certain way.
     *
     * @param wb     the workbook
     * @param row    the row to create the cell in
     * @param column the column number to create the cell in
     * @param halign the horizontal alignment for the cell.
     * @param valign the vertical alignment for the cell.
     */
    private static void createCell(Workbook wb, Row row, int column, HorizontalAlignment halign, VerticalAlignment valign) {
        Cell cell = row.createCell(column);
        cell.setCellValue("Align It");
        //CellStyle cellStyle = wb.createCellStyle();
        //cellStyle.setAlignment(halign);
        //cellStyle.setVerticalAlignment(valign);
        //cell.setCellStyle(cellStyle);
    }


    public void exportExcel(String querySql,String filePath, String ... titles) {

        try(Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet();
            // 生成第一行，标题
            Row head = sheet.createRow(0);
            for (int i = 0; i < titles.length; i++) {
                Cell cell = head.createCell(i);
                cell.setCellValue(titles[i]);
            }

            Row row = sheet.createRow(2);
            row.setHeightInPoints(30);
            createCell(wb, row, 0, HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);
            createCell(wb, row, 1, HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.BOTTOM);
            createCell(wb, row, 2, HorizontalAlignment.FILL, VerticalAlignment.CENTER);
            createCell(wb, row, 3, HorizontalAlignment.GENERAL, VerticalAlignment.CENTER);
            createCell(wb, row, 4, HorizontalAlignment.JUSTIFY, VerticalAlignment.JUSTIFY);
            createCell(wb, row, 5, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
            createCell(wb, row, 6, HorizontalAlignment.RIGHT, VerticalAlignment.TOP);
            // Write the output to a file
            try (OutputStream fileOut = new FileOutputStream("C:\\Users\\xmz\\Desktop\\test\\logs\\xssf-align.xlsx")) {
                wb.write(fileOut);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }



}
