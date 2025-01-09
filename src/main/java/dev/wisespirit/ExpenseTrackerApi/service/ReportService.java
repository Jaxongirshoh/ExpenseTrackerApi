package dev.wisespirit.ExpenseTrackerApi.service;

import dev.wisespirit.ExpenseTrackerApi.model.Transaction;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionType;
import dev.wisespirit.ExpenseTrackerApi.repository.TransactionRepository;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {
    private final TransactionRepository transactionRepository;

    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public byte[] generateMonthlyReport(int year, int month, TransactionType type) {
        List<Transaction> transactions = getTransactions(year, month, type);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Monthly Report");

            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dateStyle = createDateStyle(workbook);
            CellStyle numberStyle = createNumberStyle(workbook);

            // Create headers with style
            Row headerRow = sheet.createRow(0);
            createHeaderCell(headerRow, 0, "Date", headerStyle);
            createHeaderCell(headerRow, 1, "Amount", headerStyle);
            createHeaderCell(headerRow, 2, "Money Type", headerStyle);
            createHeaderCell(headerRow, 3, "Category/Client", headerStyle);
            createHeaderCell(headerRow, 4, "Note", headerStyle);

            // Fill data with styles
            int rowNum = 1;
            for (Transaction transaction : transactions) {
                Row row = sheet.createRow(rowNum++);

                // Date cell with date style
                Cell dateCell = row.createCell(0);
                dateCell.setCellStyle(dateStyle);
                dateCell.setCellValue(transaction.getDate());

                // Amount cell with number style
                Cell amountCell = row.createCell(1);
                amountCell.setCellStyle(numberStyle);
                amountCell.setCellValue(transaction.getAmount().doubleValue());

                // Other cells...
                row.createCell(2).setCellValue(transaction.getMoneyType().toString());
        //        row.createCell(3).setCellValue(getCategoryOrClient(transaction));
                row.createCell(4).setCellValue(transaction.getNote());
            }

            // Add summary at bottom
            addSummary(sheet, transactions, rowNum, numberStyle);

            // Auto-size and final formatting
            finalizeSheet(sheet);

            return workbookToBytes(workbook);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate report", e);
        }
    }

    private List<Transaction> getTransactions(int year, int month, TransactionType type) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusNanos(1);
        return transactionRepository.findByTypeAndDateBetween(type, startDate, endDate);
    }

    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createDateStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("dd-mm-yyyy"));
        return style;
    }

    private CellStyle createNumberStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
        return style;
    }

    private void addSummary(XSSFSheet sheet, List<Transaction> transactions, int rowNum, CellStyle numberStyle) {
        Row summaryRow = sheet.createRow(rowNum + 1);
        summaryRow.createCell(0).setCellValue("Total:");

        Cell totalCell = summaryRow.createCell(1);
        totalCell.setCellStyle(numberStyle);
        totalCell.setCellValue(calculateTotal(transactions));
    }

    private double calculateTotal(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(t -> t.getAmount().doubleValue())
                .sum();
    }

    private byte[] workbookToBytes(XSSFWorkbook workbook) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private void createHeaderCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void finalizeSheet(XSSFSheet sheet) {
        // Auto-size all columns
        for (int i = 0; i < 5; i++) { // Assuming there are 5 columns
            sheet.autoSizeColumn(i);
        }
        // Additional formatting can be added if needed
    }



    /*public byte[] generateMonthlyReport(int year, int month, TransactionType type) {
        // Get transactions for the specified month
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusNanos(1);

        List<Transaction> transactions = transactionRepository
                .findByTypeAndDateBetween(type, startDate, endDate);

        // Create Excel workbook
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Monthly Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Date");
            headerRow.createCell(1).setCellValue("Amount");
            headerRow.createCell(2).setCellValue("Money Type");
            headerRow.createCell(3).setCellValue("Category");
            headerRow.createCell(4).setCellValue("Client");
            headerRow.createCell(5).setCellValue("Service");
            headerRow.createCell(6).setCellValue("Status");
            headerRow.createCell(7).setCellValue("Note");

            // Fill data rows
            int rowNum = 1;
            for (Transaction transaction : transactions) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(transaction.getDate().toString());
                row.createCell(1).setCellValue(transaction.getAmount().doubleValue());
                row.createCell(2).setCellValue(transaction.getMoneyType().toString());

                // For expense type, add category
                if (type == TransactionType.EXPENSE) {
                    row.createCell(3).setCellValue(transaction.getExpenseCategoryId().toString());
                }

                // For income type, add client and service
                if (type == TransactionType.INCOME) {
                    row.createCell(4).setCellValue(transaction.getClientId().toString());
                    row.createCell(5).setCellValue(transaction.getServiceId().toString());
                }

                row.createCell(6).setCellValue(transaction.getStatus().toString());
                row.createCell(7).setCellValue(transaction.getNote());
            }

            // Auto-size columns
            for (int i = 0; i < 8; i++) {
                sheet.autoSizeColumn(i);
            }

            // Convert workbook to byte array
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate report", e);
        }
    }*/
}
