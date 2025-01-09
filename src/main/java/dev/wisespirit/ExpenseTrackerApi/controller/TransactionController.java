package dev.wisespirit.ExpenseTrackerApi.controller;

import dev.wisespirit.ExpenseTrackerApi.dto.TransactionCreatDto;
import dev.wisespirit.ExpenseTrackerApi.dto.TransactionDto;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionType;
import dev.wisespirit.ExpenseTrackerApi.service.ReportService;
import dev.wisespirit.ExpenseTrackerApi.service.TransActionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransActionService transActionService;
    private final ReportService reportService;

    public TransactionController(TransActionService transActionService, ReportService reportService) {
        this.transActionService = transActionService;
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@Valid @RequestBody TransactionCreatDto dto){
        return transActionService.createTransaction(dto)
                .map(tdto -> new ResponseEntity<>(tdto, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id){
        if (transActionService.existById(id)) {
            return transActionService.getTransaction(id).map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).get();
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<TransactionDto>> getMonthlyTransactions(@RequestParam int month,
                                                                       @RequestParam int year,
                                                                       @RequestBody TransactionType type){
        return transActionService.getMonthlyUpdate(month, year, type)
                .map(list -> new ResponseEntity<>(list, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/reports/monthly")
    public ResponseEntity<byte[]> getMonthlyReportAsFile(@RequestParam int month,
                                                         @RequestParam int year,
                                                         @RequestBody TransactionType type){
        byte[] bytes = reportService.generateMonthlyReport(month, year, type);
        MultiValueMap<String ,String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=report.xlsx");
        multiValueMap.add(HttpHeaders.CONTENT_TYPE,"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return new ResponseEntity<>(bytes,multiValueMap,HttpStatus.OK);

    }
}
