package com.example.springcameljpademo.controller;

import com.example.springcameljpademo.exception.RecordNotFoundException;
import com.example.springcameljpademo.model.ClientEntity;
import com.example.springcameljpademo.model.UserEntity;
import com.example.springcameljpademo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by MD3431 on 03/08/2020
 */
@RestController
@RequestMapping("/v1/demo")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public @ResponseBody
    Map<String, Object> getUsers(HttpServletRequest request,
                                 @RequestParam(value="start", required = false, defaultValue = "0") int start,
                                 @RequestParam(value="limit", required = false, defaultValue = "10") int limit,
                                 @RequestParam(value="sort", required = false) String sort) throws Exception {
        // logger.info("BookController --> Retrieve the list of books...");
        Map<String, Object> mapUsers = new HashMap<String, Object>();
        List<UserEntity> list = new ArrayList<UserEntity>();
        try{
            BigInteger total = BigInteger.ZERO;
            list = userService.getAllUsers();
            total = userService.getTotalUsers();

            mapUsers.put("data", list);
            mapUsers.put("total", total);

            return mapUsers;
        }catch(Exception e) {
            System.out.println(e.getMessage());
            // logger.error(new InvoiceLogger().reportError("OrdersController.getOrders()", e, logger));
        }
        return mapUsers;
    }

    @GetMapping("/users/{id}")
    UserEntity getUser(@PathVariable Long id) throws RecordNotFoundException {
        return userService.getUser(id);
    }

    @PostMapping("/users")
    UserEntity newUser(@RequestBody UserEntity newUser) throws RecordNotFoundException {
        return userService.newUser(newUser);
    }

    @PutMapping("/users/{id}")
    UserEntity createOrUpdateUser(@RequestBody UserEntity newUser, @PathVariable Long id) throws RecordNotFoundException {
        return userService.createOrUpdateUser(newUser, id);
    }
}
