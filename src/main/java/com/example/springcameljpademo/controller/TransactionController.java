package com.example.springcameljpademo.controller;

import com.example.springcameljpademo.domain.TransactionItem;
import com.example.springcameljpademo.exception.RecordNotFoundException;
import com.example.springcameljpademo.model.TransactionEntity;
import com.example.springcameljpademo.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MD3431 on 03/08/2020
 */
@RestController
@RequestMapping("/v1/demo")
@Api("TransactionController Relevant api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    @ApiOperation("Getting list of transactions")
    public @ResponseBody
    Map<String, Object> getTransactions(HttpServletRequest request,
                                   @RequestParam(value="start", required = false, defaultValue = "0") int start,
                                   @RequestParam(value="limit", required = false, defaultValue = "10") int limit,
                                   @RequestParam(value="sort", required = false) String sort) throws Exception {
        Map<String, Object> mapTransactions = new HashMap<String, Object>();
        List<TransactionEntity> list = new ArrayList<TransactionEntity>();
        try{
            BigInteger total = BigInteger.ZERO;
            list = transactionService.getAllTransactions();
            total = transactionService.getTotalTransactions();

            mapTransactions.put("data", list);
            mapTransactions.put("total", total);

            return mapTransactions;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return mapTransactions;
    }

    @GetMapping("/transactions/{id}")
    @ApiOperation(value = "Getting specific transaction", notes = "According to its id")
    TransactionEntity getTransaction(@PathVariable Long id) throws RecordNotFoundException {
        return transactionService.getTransaction(id);
    }

    @PostMapping("/transactions")
    @ApiOperation(value = "Creating transaction", notes = "According to Transaction Entity")
    TransactionEntity newTransaction(@RequestBody TransactionItem newTransaction) throws RecordNotFoundException {
        return transactionService.newTransaction(newTransaction);
    }

    @PutMapping("/transactions/{id}")
    @ApiOperation(value = "Creating or updating transaction", notes = "According to new Transaction Entity, and its id")
    TransactionEntity putTransaction(@RequestBody TransactionItem newTransaction, @PathVariable Long id) throws RecordNotFoundException {
        return transactionService.putTransaction(newTransaction, id);
    }

    @DeleteMapping("/transactions/{id}")
    @ApiOperation(value = "Deleting specific transaction", notes = "According to its id")
    public HttpStatus deleteTransactionById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        transactionService.deleteTransactionById(id);
        return HttpStatus.ACCEPTED;
    }
}