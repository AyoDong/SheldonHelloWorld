package com.sheldon.ai;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExcelToSqlConverter {
    
    /**
     * 将驼峰命名转换为下划线命名
     */
    public static String camelToSnake(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        
        // 在大写字母前添加下划线，然后转换为小写
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        String snakeCase = name.replaceAll(regex, replacement).toLowerCase();
        
        // 处理连续的大写字母
        regex = "([A-Z])([A-Z][a-z])";
        replacement = "$1_$2";
        snakeCase = snakeCase.replaceAll(regex, replacement).toLowerCase();
        
        // 特殊处理：将 contractno 转换为 contract_no
        if ("contractno".equals(snakeCase)) {
            snakeCase = "contract_no";
        }
        
        return snakeCase;
    }
    
    /**
     * 读取Excel文件并生成SQL insert ... on duplicate key update语句
     * 返回按表名分组的SQL语句映射
     */
    public static Map<String, List<String>> generateSqlFromExcel(String excelFilePath, String tableName) {
        Map<String, List<String>> sqlStatementsByTable = new HashMap<>();
        
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            // 遍历所有sheet
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String currentSheetName = sheet.getSheetName();
                System.out.println("处理Sheet: " + currentSheetName);
                
                if (sheet.getPhysicalNumberOfRows() == 0) {
                    System.out.println("Sheet '" + currentSheetName + "' 为空，跳过");
                    continue;
                }
                
                // 读取第一行作为列名
                Row headerRow = sheet.getRow(0);
                if (headerRow == null) {
                    System.out.println("Sheet '" + currentSheetName + "' 没有表头，跳过");
                    continue;
                }
                
                List<String> columnNames = new ArrayList<>();
                List<String> snakeColumnNames = new ArrayList<>();
                
                // 获取列名并转换为下划线命名
                Iterator<Cell> cellIterator = headerRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String columnName = getCellValueAsString(cell);
                    columnNames.add(columnName);
                    snakeColumnNames.add(camelToSnake(columnName));
                }
                
                // 确定表名
                String currentTableName = (tableName != null && !tableName.isEmpty()) ? 
                        tableName : currentSheetName;
                
                // 处理数据行
                List<String> sqlStatements = sqlStatementsByTable.computeIfAbsent(currentTableName, k -> new ArrayList<>());
                int originalSize = sqlStatements.size();
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    Row dataRow = sheet.getRow(rowNum);
                    if (dataRow == null) {
                        continue;
                    }
                    
                    // 如果是 rent_enterprise 页签，检查 name 列是否包含特定内容
                    if ("rent_enterprise".equalsIgnoreCase(currentSheetName)) {
                        // 获取 name 列的值
                        Cell nameCell = dataRow.getCell(columnNames.indexOf("name"), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        String nameValue = getCellValueAsString(nameCell);
                        
                        // 如果 name 包含 '停车费-' 或 '临停费用_'，则跳过该行
                        if (nameValue != null && (nameValue.contains("停车费-") || nameValue.contains("临停费用_"))) {
                            continue;
                        }
                    }
                    
                    // 构建VALUES部分
                    List<String> values = new ArrayList<>();
                    for (int colNum = 0; colNum < columnNames.size(); colNum++) {
                        Cell cell = dataRow.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        String value = getCellValueAsString(cell);
                        
                        if (value == null || value.isEmpty() || "/".equals(value.trim()) || "/N".equals(value.trim()) || "\\N".equals(value.trim())) {
                            values.add("NULL");
                        } else if (isNumeric(value) && !shouldTreatAsString(currentSheetName, columnNames.get(colNum))) {
                            values.add(value);
                        } else {
                            // 转义单引号
                            String escapedValue = value.replace("'", "''");
                            values.add("'" + escapedValue + "'");
                        }
                    }
                    
                    // 构建ON DUPLICATE KEY UPDATE部分
                    List<String> updateClauses = new ArrayList<>();
                    for (String column : snakeColumnNames) {
                        updateClauses.add("`" + column + "` = VALUES(`" + column + "`)");
                    }
                    
                    // 构建完整的SQL语句
                    StringBuilder sql = new StringBuilder();
                    sql.append("INSERT INTO `").append(currentTableName).append("` (")
                       .append(String.join(", ", snakeColumnNames.stream().map(col -> "`" + col + "`").collect(Collectors.toList())))
                       .append(", `insert_time`"); // 添加 insert_time 字段
                    
                    // 如果是 rent_enterprise 页签，添加 community_id 字段
                    if ("rent_enterprise".equalsIgnoreCase(currentSheetName)) {
                        sql.append(", `community_id`");
                    }
                    
                    sql.append(") ")
                       .append("VALUES (")
                       .append(String.join(", ", values))
                       .append(", NOW()"); // 添加当前时间值
                    
                    // 如果是 rent_enterprise 页签，添加 community_id 值（从 organ_id 获取）
                    if ("rent_enterprise".equalsIgnoreCase(currentSheetName)) {
                        // 获取 organ_id 列的值
                        int organIdIndex = columnNames.indexOf("organ_id");
                        if (organIdIndex >= 0) {
                            Cell organIdCell = dataRow.getCell(organIdIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String organIdValue = getCellValueAsString(organIdCell);
                            if (organIdValue != null && !organIdValue.isEmpty() && !"/".equals(organIdValue.trim()) && 
                                !"/N".equals(organIdValue.trim()) && !"\\N".equals(organIdValue.trim())) {
                                sql.append(", ").append(organIdValue);
                            } else {
                                sql.append(", NULL");
                            }
                        } else {
                            sql.append(", NULL");
                        }
                    }
                    
                    sql.append(") ")
                       .append("ON DUPLICATE KEY UPDATE ")
                       .append(String.join(", ", updateClauses));
                    
                    // 如果是 rent_enterprise 页签，添加 community_id 的更新
                    if ("rent_enterprise".equalsIgnoreCase(currentSheetName)) {
                        sql.append(", `community_id` = VALUES(`community_id`)");
                    }
                    
                    sql.append(", `insert_time` = NOW()"); // 更新时也更新 insert_time
                    
                    sql.append(";");
                    
                    sqlStatements.add(sql.toString());
                    
                    // 如果是 rent_lease_contract 页签，处理 resIds 字段并生成 rent_room_contract 语句
                    if ("rent_lease_contract".equalsIgnoreCase(currentSheetName)) {
                        // 获取 resIds 列的值
                        int resIdsIndex = columnNames.indexOf("resIds");
                        if (resIdsIndex >= 0) {
                            Cell resIdsCell = dataRow.getCell(resIdsIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String resIdsValue = getCellValueAsString(resIdsCell);
                            
                            if (resIdsValue != null && !resIdsValue.isEmpty() && !"/".equals(resIdsValue.trim()) && 
                                !"/N".equals(resIdsValue.trim()) && !"\\N".equals(resIdsValue.trim())) {
                                
                                // 获取 contractId 列的值
                                int contractIdIndex = columnNames.indexOf("contractId");
                                String contractIdValue = "";
                                if (contractIdIndex >= 0) {
                                    Cell contractIdCell = dataRow.getCell(contractIdIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                    contractIdValue = getCellValueAsString(contractIdCell);
                                }
                                
                                // 拆分 resIds 字符串
                                String[] ids = resIdsValue.split(",");
                                for (String id : ids) {
                                    String trimmedId = id.trim();
                                    if (!trimmedId.isEmpty()) {
                                        // 构建 rent_room_contract 的 SQL 语句
                                        StringBuilder roomContractSql = new StringBuilder();
                                        roomContractSql.append("INSERT INTO `rent_room_contract` (`id`, `contract_id`, `res_id`, `insert_time`) ")
                                                       .append("VALUES ('")
                                                       .append(contractIdValue).append("_").append(trimmedId).append("', '")
                                                       .append(contractIdValue).append("', '")
                                                       .append(trimmedId).append("', NOW()) ")
                                                       .append("ON DUPLICATE KEY UPDATE ")
                                                       .append("`contract_id` = VALUES(`contract_id`), ")
                                                       .append("`res_id` = VALUES(`res_id`), ")
                                                       .append("`insert_time` = NOW()")
                                                       .append(";");
                                        
                                        sqlStatements.add(roomContractSql.toString());
                                    }
                                }
                            }
                        }
                    }
                }
                
                System.out.println("Sheet '" + currentSheetName + "' 处理完成，生成 " + 
                        (sqlStatements.size() - originalSize) + " 条SQL语句");
            }

            System.out.println();
            
        } catch (IOException e) {
            System.err.println("处理文件时出错: " + e.getMessage());
            e.printStackTrace();
        }
        
        return sqlStatementsByTable;
    }
    
    /**
     * 获取单元格的值作为字符串
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // 处理数字，避免科学计数法
                    double num = cell.getNumericCellValue();
                    if (num == (long) num) {
                        return String.valueOf((long) num);
                    } else {
                        return String.valueOf(num);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
    
    /**
     * 检查字符串是否为数字
     */
    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 检查特定表的特定字段是否应该作为字符串处理
     */
    private static boolean shouldTreatAsString(String sheetName, String columnName) {
        // rent_enterprise 表的 cardNbr 字段应该始终作为字符串处理
        if ("rent_enterprise".equalsIgnoreCase(sheetName) && "cardNbr".equalsIgnoreCase(columnName)) {
            return true;
        }
        // rent_lease_contract 表的 secondCredNo 字段应该始终作为字符串处理
        if ("rent_lease_contract".equalsIgnoreCase(sheetName) && "secondCredNo".equalsIgnoreCase(columnName)) {
            return true;
        }
        return false;
    }
    
    /**
     * 将SQL语句按表名保存到不同的文件夹
     */
    public static void saveSqlToFiles(Map<String, List<String>> sqlStatementsByTable, String outputDirPath) {
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        
        for (Map.Entry<String, List<String>> entry : sqlStatementsByTable.entrySet()) {
            String tableName = entry.getKey();
            List<String> sqlStatements = entry.getValue();
            
            // 创建表对应的文件夹
            File tableDir = new File(outputDir, tableName);
            if (!tableDir.exists()) {
                tableDir.mkdirs();
            }
            
            // 保存SQL文件
            String outputFilePath = new File(tableDir, tableName + ".sql").getAbsolutePath();
            try (PrintWriter writer = new PrintWriter(new FileOutputStream(outputFilePath))) {
                for (String sql : sqlStatements) {
                    writer.println(sql);
                }
                System.out.println("表 " + tableName + " 的SQL语句已保存到: " + outputFilePath);
            } catch (IOException e) {
                System.err.println("保存表 " + tableName + " 的文件时出错: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        // 文件路径
        String excelFilePath = "src/main/java/com/sheldon/ai/rent_data.xlsx";
        String outputDirPath = "src/main/java/com/sheldon/ai/sql_output";
        
        // 可选：指定表名，如果为null则使用sheet名作为表名
        String tableName = null; // 或者指定表名，如 "rent_data"
        
        // 生成SQL语句
        Map<String, List<String>> sqlStatementsByTable = generateSqlFromExcel(excelFilePath, tableName);
        
        if (!sqlStatementsByTable.isEmpty()) {
            // 保存到文件
            saveSqlToFiles(sqlStatementsByTable, outputDirPath);
            
            // 在控制台显示统计信息
            System.out.println("\nSQL语句生成统计:");
            int totalStatements = 0;
            for (Map.Entry<String, List<String>> entry : sqlStatementsByTable.entrySet()) {
                int count = entry.getValue().size();
                System.out.println("表 " + entry.getKey() + ": " + count + " 条语句");
                totalStatements += count;
            }
            System.out.println("总计: " + totalStatements + " 条SQL语句");
        } else {
            System.out.println("未生成任何SQL语句");
        }
    }
}