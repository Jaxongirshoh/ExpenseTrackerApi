package dev.wisespirit.ExpenseTrackerApi.service;

import dev.wisespirit.ExpenseTrackerApi.dto.TransactionCreatDto;
import dev.wisespirit.ExpenseTrackerApi.dto.TransactionDto;
import dev.wisespirit.ExpenseTrackerApi.model.Transaction;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionType;
import dev.wisespirit.ExpenseTrackerApi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransActionService {
    private final TransactionRepository transactionRepository;

    public TransActionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
        //todo synch with notion
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
