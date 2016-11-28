package com.jngld.doc.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * excel读写工具类
 * @author (作者) xus-a
 * @version (版本) V1.0
 * @date (开发日期) 2015年11月27日 下午4:30:48
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 * @since (该版本支持的JDK版本) 1.7
 */
public class ExcelUtil {
    private static final int XLS_CODE = 1;
    private static final int XLSX_CODE = 2;

    /**
     * 导出03版(后缀xls)的excel(无分页，数据只在一个sheet中)
     * @param out
     * @param dataList 数据集合，最外层是行，List<Object>行内列的数据集合
     * @param titleArray 标题集合数组
     * @throws IOException
     * @author xus-a
     * @date 2015年11月30日 下午2:10:17
     */
    public static void exprotExcel2003(OutputStream out,
                                       List<List<Object>> dataList, String[] titleArray) throws IOException {
        exportExcel(out, dataList, titleArray, XLS_CODE);
    }

    /**
     * 导出03版(后缀xls)的excel(分页，数据会在多个sheet中，页数大小自己指定)
     * @param out
     * @param dataList 数据集合，最外层是行，List<Object>行内列的数据集合
     * @param titleArray 标题集合数组
     * @param pageSize
     * @throws IOException
     * @author xus-a
     * @date 2015年11月30日 下午2:10:21
     */
    public static void exprotExcel2003(OutputStream out,
                                       List<List<Object>> dataList, String[] titleArray, int pageSize)
            throws IOException {
        exportExcel(out, dataList, titleArray, pageSize, XLS_CODE);
    }

    /**
     * 导出07版(后缀xls)的excel(无分页，数据只在一个sheet中)
     * @param out
     * @param dataList 数据集合，最外层是行，List<Object>行内列的数据集合
     * @param titleArray 标题集合数组
     * @throws IOException
     * @author xus-a
     * @date 2015年11月30日 下午2:10:25
     */
    public static void exprotExcel2007(OutputStream out,
                                       List<List<Object>> dataList, String[] titleArray) throws IOException {
        exportExcel(out, dataList, titleArray, XLSX_CODE);
    }

    /**
     * 导出07版(后缀xls)的excel(分页，数据会在多个sheet中，页数大小自己指定)
     * @param out
     * @param dataList 数据集合，最外层是行，List<Object>行内列的数据集合
     * @param titleArray 标题集合数组
     * @param pageSize
     * @throws IOException
     * @author xus-a
     * @date 2015年11月30日 下午2:10:28
     */
    public static void exprotExcel2007(OutputStream out,
                                       List<List<Object>> dataList, String[] titleArray, int pageSize)
            throws IOException {
        exportExcel(out, dataList, titleArray, pageSize, XLSX_CODE);
    }

    /**
     * 导出excel(无分页)
     * @param out
     * @param dataList
     * @param titleArray
     * @throws IOException
     * @author xus-a
     * @date 2015年11月30日 下午1:26:01
     */
    private static void exportExcel(OutputStream out, List<List<Object>> dataList,
                                    String[] titleArray, int verCode) throws IOException {
        Workbook workbook = null;
        if (verCode == XLS_CODE) {
            workbook = new HSSFWorkbook();
        }
        if (verCode == XLSX_CODE) {
            workbook = new XSSFWorkbook();
        }
        createSheet(workbook, dataList, titleArray, 0);
        try {
            workbook.write(out);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    /**
     * 导出excel(分页)
     * @param out
     * @param dataList 需要传入的数据源,最外面一层List代表的是你的行,里面的List代表行里的数据
     * @param titleArray
     * @param pageSize
     * @throws IOException
     * @author xus-a
     * @date 2015年11月30日 下午1:26:05
     */
    private static void exportExcel(OutputStream out, List<List<Object>> dataList,
                                    String[] titleArray, int pageSize, int verCode) throws IOException {
        Workbook workbook = null;
        try {
            if (verCode == XLS_CODE) {
                workbook = new HSSFWorkbook();
            }
            if (verCode == XLSX_CODE) {
                workbook = new XSSFWorkbook();
            }
            // 取出共有多少页
            int totalPage = getPageTotal(dataList.size(), pageSize);
            // 分页(将分页方法抽到这里，分割List，逻辑要简单些。)
            for (int sheetIndex = 0; sheetIndex < totalPage; sheetIndex++) {
                List<List<Object>> subList = null;
                // 最后一页截到末尾
                if (sheetIndex == totalPage - 1) {
                    subList = dataList.subList(sheetIndex * pageSize, dataList.size());
                } else {
                    subList = dataList.subList(sheetIndex * pageSize,
                            (sheetIndex + 1) * pageSize);
                }
                // 创建sheet
                createSheet(workbook, subList, titleArray, sheetIndex);
            }
            workbook.write(out);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    /**
     * 创建sheet的方法
     * @param workbook poi
     * @param dataList 数据集合
     * @param titleArray 表头数组
     * @param sheetIndex sheet的序号
     * @throws IOException
     * @author xus-a
     * @date 2015年11月30日 下午1:21:35
     */
    private static void createSheet(Workbook workbook,
                                    List<List<Object>> dataList, String[] titleArray, int sheetIndex)
            throws IOException {
        Sheet sheet = workbook.createSheet(String.valueOf(sheetIndex));
        Row row0 = sheet.createRow(0);
        // 在第一行创建表头
        for (int i = 0; i < titleArray.length; i++) {
            Cell cell = row0.createCell(i, Cell.CELL_TYPE_STRING);
            cell.setCellValue(titleArray[i]);
        }
        // -----------插入数据
        // 行
        for (int rowNum = 0; rowNum < dataList.size(); rowNum++) {
            Row row = sheet.createRow(rowNum + 1);
            List<Object> rowlist = dataList.get(rowNum);
            // 列
            for (int i = 0; i < rowlist.size(); i++) {
                // 这一行的填充数据
                Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
                if (rowlist.get(i) != null) {
                    cell.setCellValue(rowlist.get(i).toString());
                }
            }
        }
    }

    /**
     * 计算分页
     * @param total
     * @param pageSize
     * @return
     * @author xus-a
     * @date 2015年11月30日 下午1:21:17
     */
    private static int getPageTotal(int total, int pageSize) {
        int totalPage = 0;
        if (pageSize != 0) {
            totalPage = (total % pageSize == 0) ? (total / pageSize)
                    : (total / pageSize) + 1;
        }
        return totalPage;
    }

    /**
     * @param path xls文件路径
     * @return 外层List<List<String>>代表的是行,List<String>代表的行内数据集合，按照顺序解析
     * @throws IOException
     * @Description 解析03版本xls格式的excel
     * @author xus-a
     * @date 2015年12月1日 上午10:50:19
     */
    public static List<List<String>> readXls(String path) throws IOException {
        File file = new File(path);
        List<List<String>> resList = readXls(file);
        return resList;
    }

    /**
     * @param in xls的输入流
     * @return 外层List<List<String>>代表的是行,List<String>代表的行内数据集合，按照顺序解析
     * @throws IOException
     * @Description 解析03版本xls格式的excel
     * @author xus-a
     * @date 2015年12月1日 上午10:50:43
     */
    public static List<List<String>> readXls(InputStream in) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("输入流不能为空");
        }
        List<List<String>> resList = null;
        try {
            resList = parseXls(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return resList;
    }

    /**
     * @param file
     * @return
     * @throws IOException
     * @Description 解析03版本xls格式的excel
     * @author xus-a
     * @date 2015年12月1日 下午3:23:17
     */
    public static List<List<String>> readXls(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("未找到文件");
        }
        List<List<String>> list = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            list = readXls(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return list;
    }

    /**
     * @param path xlsx格式的excel文件路径
     * @return 外层List<List<String>>代表的是行,List<String>代表的行内数据集合，按照顺序解析
     * @throws IOException
     * @Description 解析07版本xlsx格式的excel
     * @author xus-a
     * @date 2015年12月1日 上午10:52:26
     */
    public static List<List<String>> readXlsx(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("未找到文件");
        }
        List<List<String>> resList = null;
        resList = readXlsx(file);
        return resList;
    }

    /**
     * @param in xlsx类型excel的输入流
     * @return 外层List<List<String>>代表的是行,List<String>代表的行内数据集合，按照顺序解析
     * @throws IOException
     * @Description 解析07版本xlsx格式的excel
     * @author xus-a
     * @date 2015年12月1日 上午10:52:32
     */
    public static List<List<String>> readXlsx(InputStream in) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("输入流不能为空");
        }
        List<List<String>> resList = null;
        try {
            resList = parseXlsx(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return resList;
    }

    /**
     * @param file
     * @return
     * @throws IOException
     * @Description 解析07版本xlsx格式的excel
     * @author xus-a
     * @date 2015年12月1日 下午3:01:09
     */
    public static List<List<String>> readXlsx(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("未找到文件");
        }
        InputStream in = null;
        List<List<String>> resList = null;
        try {
            in = new FileInputStream(file);
            resList = readXlsx(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return resList;
    }

    /**
     * @param in
     * @throws IOException
     * @Description 03版读取excel
     * @author xus-a
     * @date 2015年11月30日 下午3:49:46
     */
    private static List<List<String>> parseXls(InputStream in)
            throws IOException {
        List<List<String>> resList = new ArrayList<List<String>>();
        HSSFWorkbook hssfWorkbook = null;
        try {
            hssfWorkbook = new HSSFWorkbook(in);

            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            // 读取行
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                List<String> rowContent = new ArrayList<String>();
                for (int j = 0; j < hssfRow.getLastCellNum(); j++) {
                    rowContent.add(getCell(hssfRow.getCell(j)));
                }
                resList.add(rowContent);
            }
        } finally {
            if (hssfWorkbook != null) {
                hssfWorkbook.close();
            }
        }
        return resList;
    }

    /**
     * @param in
     * @throws IOException
     * @Description 07版读取excel
     * @author xus-a
     * @date 2015年11月30日 下午2:53:50
     */
    private static List<List<String>> parseXlsx(InputStream in)
            throws IOException {
        List<List<String>> resList = new ArrayList<List<String>>();
        // 构造 XSSFWorkbook 对象
        XSSFWorkbook xwb = null;
        try {
            xwb = new XSSFWorkbook(in);
            // 读取第一章表格内容
            XSSFSheet sheet = xwb.getSheetAt(0);
            // 定义 row、cell
            XSSFRow row;
            // 循环输出表格中的内容
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                List<String> rowContent = new ArrayList<String>();
                row = sheet.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    rowContent.add(getCell(row.getCell(j)));
                }
                resList.add(rowContent);
            }
        } finally {
            if (xwb != null) {
                xwb.close();
            }
        }
        return resList;
    }

    /**
     * @param cell
     * @return
     * @Description 根据返回的不同类型去返回不同的值
     * @author xus-a
     * @date 2015年11月30日 下午3:18:59
     */
    private static String getCell(Cell cell) {
        DecimalFormat df = new DecimalFormat("#");
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()))
                            .toString();
                }
                return df.format(cell.getNumericCellValue());
            case XSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case XSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case XSSFCell.CELL_TYPE_BLANK:
                return "";
            case XSSFCell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";
            case XSSFCell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue() + "";
        }
        return "";
    }

    /**
     * 判断行传入行是否为空
     * @param row
     * @return
     */
    public static boolean rowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        short len = row.getLastCellNum();
        for (int i = 0; i < len; i++) {
            if (cellIsNotEmpty(row.getCell(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean rowIsNotEmpty(Row row) {
        return !rowIsEmpty(row);
    }

    /**
     * 判断传入单元格是否为空
     * @param cell
     * @return
     */
    public static boolean cellIsEmpty(Cell cell) {
        if (cell == null) {
            return true;
        }
        try {
            String cellStr = cell.toString();
            if (cellStr.trim().length() == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean cellIsNotEmpty(Cell cell) {
        return !cellIsEmpty(cell);
    }

}
