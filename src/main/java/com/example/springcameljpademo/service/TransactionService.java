package com.example.springcameljpademo.service;

import com.example.springcameljpademo.domain.TransactionItem;
import com.example.springcameljpademo.exception.RecordNotFoundException;
import com.example.springcameljpademo.model.ClientEntity;
import com.example.springcameljpademo.model.TransactionEntity;
import com.example.springcameljpademo.model.UserEntity;
import com.example.springcameljpademo.repository.ClientRepository;
import com.example.springcameljpademo.repository.TransactionRepository;
import com.example.springcameljpademo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by MD3431 on 03/08/2020
 */
@Service(value = "transactionService")
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    public List<TransactionEntity> getAllTransactions() {
        List<TransactionEntity> transactionList = transactionRepository.findAll();
        if(transactionList.size() > 0) {
            return transactionList;
        } else {
            return new ArrayList<TransactionEntity>();
        }
    }

    public BigInteger getTotalTransactions() {
        List<TransactionEntity> transactionList = transactionRepository.findAll();
        if(transactionList.size() > 0) {
            return BigInteger.valueOf(transactionList.size());
        } else {
            return BigInteger.ZERO;
        }
    }

    public TransactionEntity getTransaction(Long id) throws RecordNotFoundException{
        Optional<TransactionEntity> transaction = transactionRepository.findById(id);
        if(transaction.isPresent()) {
            return transaction.get();
        } else {
            throw new RecordNotFoundException("No transaction record exist for given id");
        }
    }

    public TransactionEntity newTransaction(TransactionItem newTransaction) throws RecordNotFoundException{
        TransactionEntity entity = transformItemToEntity(newTransaction);
        return transactionRepository.save(entity);
    }

    public TransactionEntity putTransaction(TransactionItem newTransaction, Long id) throws RecordNotFoundException {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    try {
                        transaction.setUser(userService.getUser(newTransaction.getUserId()));
                        transaction.setClient(clientService.getClient(newTransaction.getClientId()));
                    } catch (RecordNotFoundException e) {
                        e.printStackTrace();
                    }
                    transaction.setStock(newTransaction.getStock());
                    transaction.setType(newTransaction.getType());
                    transaction.setPrice(newTransaction.getPrice());
                    transaction.setQuantity(newTransaction.getQuantity());
                    return transactionRepository.save(transaction);
                })
                .orElseGet(() -> {
                    TransactionEntity entity = null;
                    try {
                        entity = transformItemToEntity(newTransaction);
                    } catch (RecordNotFoundException e) {
                        e.printStackTrace();
                    }
                    return transactionRepository.save(entity);
                });
    }

    private TransactionEntity transformItemToEntity(TransactionItem transactionItem) throws RecordNotFoundException {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionId(null);
        transaction.setStock(transactionItem.getStock());
        transaction.setType(transactionItem.getType());
        transaction.setPrice(transactionItem.getPrice());
        transaction.setQuantity(transactionItem.getQuantity());
        transaction.setUser(userService.getUser(transactionItem.getUserId()));
        transaction.setClient(clientService.getClient(transactionItem.getClientId()));
        return transaction;
    }

    public void deleteTransactionById(Long id) throws RecordNotFoundException {
        Optional<TransactionEntity> transaction = transactionRepository.findById(id);
        if(transaction.isPresent()) {
            transactionRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No transaction record exist for that given id = "+id);
        }
    }
}