package dev.wisespirit.ExpenseTrackerApi.service;

import dev.wisespirit.ExpenseTrackerApi.dto.TransactionCreatDto;
import dev.wisespirit.ExpenseTrackerApi.dto.TransactionDto;
import dev.wisespirit.ExpenseTrackerApi.model.Transaction;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionType;
import dev.wisespirit.ExpenseTrackerApi.notion.NotionSyncService;
import dev.wisespirit.ExpenseTrackerApi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransActionService {
    private final TransactionRepository transactionRepository;
    private final NotionSyncService notionSyncService;

    public TransActionService(TransactionRepository transactionRepository, NotionSyncService notionSyncService) {
        this.transactionRepository = transactionRepository;
        this.notionSyncService = notionSyncService;
    }

    public Optional<TransactionDto> createTransaction(TransactionCreatDto dto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.amount());
        transaction.setDate(dto.date());
        transaction.setServiceId(dto.serviceId());
        transaction.setType(dto.type());
        transaction.setClientId(dto.clientId());
        transaction.setMoneyType(dto.moneyType());
        if (dto.note() != null) {
            transaction.setNote(dto.note());
        }
        if (dto.fileUrl() != null) {
            transaction.setFileUrl(dto.fileUrl());
        }
        Transaction saved = transactionRepository.save(transaction);
        //synch to notion
        notionSyncService.syncTransactionToNotion(saved);
        return Optional.of(convertEntityToDto(saved));
    }

    public Optional<List<TransactionDto>> getMonthlyUpdate(int year, int month, TransactionType type){
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findAll().forEach(transactions::add);
        return Optional.of(transactions.stream()
                .filter(t -> t.getType() == type)
                .filter(t -> t.getDate().getYear() == year && t.getDate().getMonthValue() == month)
                .map(TransActionService::convertEntityToDto)
                .toList());
    }

    public Optional<TransactionDto> getTransaction(Long id) {
        return transactionRepository.findById(id).map(TransActionService::convertEntityToDto);
    }

    public boolean existById(Long id) {
        return transactionRepository.existsById(id);
    }

    public static TransactionDto convertEntityToDto(Transaction transaction) {
        return new TransactionDto(transaction.getAmount(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getMoneyType(),
                transaction.getClientId(),
                transaction.getServiceId(),
                transaction.getNote(),
                transaction.getFileUrl());
    }
}
