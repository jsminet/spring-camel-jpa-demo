package com.example.springcameljpademo.service;

import com.example.springcameljpademo.exception.RecordNotFoundException;
import com.example.springcameljpademo.model.ClientEntity;
import com.example.springcameljpademo.model.UserEntity;
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
@Service(value = "userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity getUser(Long id) throws RecordNotFoundException{
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new RecordNotFoundException("No User record exist for given id");
        }
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> userList = userRepository.findAll();
        if(userList.size() > 0) {
            return userList;
        } else {
            return new ArrayList<UserEntity>();
        }
    }

    public BigInteger getTotalUsers() {
        List<UserEntity> bookList = userRepository.findAll();
        if(bookList.size() > 0) {
            return BigInteger.valueOf(bookList.size());
        } else {
            return BigInteger.ZERO;
        }
    }

    public UserEntity newUser(UserEntity newEntity) throws RecordNotFoundException
    {
        return userRepository.save(newEntity);
    }

    public UserEntity createOrUpdateUser(UserEntity userEntity, Long id) throws RecordNotFoundException
    {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userEntity.getFirstName());
                    user.setLastName(userEntity.getLastName());
                    user.setTrigram(userEntity.getTrigram());
                    user.setTransactions(userEntity.getTransactions());
                    user.setClients(userEntity.getClients());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    return userRepository.save(userEntity);
                });
    }

    public void deleteUserById(Long id) throws RecordNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user record exist for that given id = "+id);
        }
    }
}
