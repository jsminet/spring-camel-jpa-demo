package com.example.springcameljpademo.controller;

import com.example.springcameljpademo.exception.RecordNotFoundException;
import com.example.springcameljpademo.model.ClientEntity;
import com.example.springcameljpademo.service.ClientService;
import com.example.springcameljpademo.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
@ApiIgnore
public class UserClientController {

    @Autowired
    private UserClientService userClientService;

    @DeleteMapping("userclient/{id}")
    public HttpStatus deleteTransactionById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        userClientService.deleteUserClientById(id);
        return HttpStatus.ACCEPTED;
    }
}