package dev.wisespirit.ExpenseTrackerApi.service;

import dev.wisespirit.ExpenseTrackerApi.model.Transaction;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionType;
import dev.wisespirit.ExpenseTrackerApi.repository.TransactionRepository;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {
    private final TransactionRepository transactionRepository;

    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    //written help of ai
    public byte[] generateMonthlyReport(int year, int month, TransactionType type) {
        // Get data
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusNanos(1);

        List<Transaction> transactions = transactionRepository
                .findByTypeAndDateBetween(type, startDate, endDate);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Monthly Report");

            // Create styles
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            CellStyle amountStyle = workbook.createCellStyle();
            amountStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));

            // Create headers
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Date", "Amount", "Money Type", "Category/Client", "Service", "Status", "Note"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Fill data
            int rowNum = 1;
            BigDecimal total = BigDecimal.ZERO;

            for (Transaction transaction : transactions) {
                Row row = sheet.createRow(rowNum++);

                // Date
                row.createCell(0).setCellValue(
                        transaction.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                );

                // Amount
                Cell amountCell = row.createCell(1);
                amountCell.setCellValue(transaction.getAmount().doubleValue());
                amountCell.setCellStyle(amountStyle);
                total = total.add(transaction.getAmount());

                // Money Type
                row.createCell(2).setCellValue(transaction.getMoneyType().toString());

                // Category/Client
                row.createCell(3).setCellValue(type == TransactionType.EXPENSE ?
                        TransactionType.EXPENSE.toString():
                        transaction.getClientId().toString());

                // Service
                row.createCell(4).setCellValue(
                        transaction.getServiceId() != null ? transaction.getServiceId().toString() : ""
                );

                // Status
                row.createCell(5).setCellValue(
                        transaction.getStatus() != null ? transaction.getStatus().toString() : ""
                );

                // Note
                row.createCell(6).setCellValue(
                        transaction.getNote() != null ? transaction.getNote() : ""
                );
            }

            // Add total row
            Row totalRow = sheet.createRow(rowNum);
            totalRow.createCell(0).setCellValue("Total:");
            Cell totalAmountCell = totalRow.createCell(1);
            totalAmountCell.setCellValue(total.doubleValue());
            totalAmountCell.setCellStyle(amountStyle);

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Convert to byte array
            try (ByteArrayOutputStream outputStream =
                         new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate report", e);
        }
    }
}
