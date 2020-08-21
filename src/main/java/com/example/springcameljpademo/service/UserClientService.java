package com.example.springcameljpademo.service;

import com.example.springcameljpademo.exception.RecordNotFoundException;
import com.example.springcameljpademo.model.ClientEntity;
import com.example.springcameljpademo.model.TransactionEntity;
import com.example.springcameljpademo.model.UserClientEntity;
import com.example.springcameljpademo.repository.ClientRepository;
import com.example.springcameljpademo.repository.UserClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by MD3431 on 06/08/2020
 */
@Service(value = "userClientService")
public class UserClientService {

    @Autowired
    private UserClientRepository userClientRepository;

    public void deleteUserClientById(Long id) throws RecordNotFoundException {
        Optional<UserClientEntity> userClient = userClientRepository.findById(id);

        if(userClient.isPresent()) {
            userClientRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No userClient record exist for that given id = "+id);
        }
    }
}
