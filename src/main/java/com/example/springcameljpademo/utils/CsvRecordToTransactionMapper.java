package com.example.springcameljpademo.utils;

import com.example.springcameljpademo.domain.TransactionCsvItem;
import com.example.springcameljpademo.exception.RecordNotFoundException;
import com.example.springcameljpademo.model.ClientEntity;
import com.example.springcameljpademo.model.TransactionEntity;
import com.example.springcameljpademo.model.UserEntity;
import com.example.springcameljpademo.service.ClientService;
import com.example.springcameljpademo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by MD3431 on 12/08/2020
 */
@Component
public class CsvRecordToTransactionMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    public TransactionEntity convertAndTransform(TransactionCsvItem csvRecord) throws RecordNotFoundException {
        //TODO using Lombok builder
        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionId(null);
        transaction.setStock(csvRecord.getStock());
        transaction.setType(csvRecord.getType());
        transaction.setPrice(csvRecord.getPrice());
        transaction.setQuantity(csvRecord.getQuantity());
        transaction.setUser(userService.getUser(csvRecord.getUserId()));
        transaction.setClient(clientService.getClient(csvRecord.getClientId()));
        return transaction;
    }
}