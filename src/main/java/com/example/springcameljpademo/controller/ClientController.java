package com.example.springcameljpademo.controller;

import com.example.springcameljpademo.domain.TransactionItem;
import com.example.springcameljpademo.exception.RecordNotFoundException;
import com.example.springcameljpademo.model.ClientEntity;
import com.example.springcameljpademo.model.TransactionEntity;
import com.example.springcameljpademo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public @ResponseBody
    Map<String, Object> getClients(HttpServletRequest request,
                                      @RequestParam(value="start", required = false, defaultValue = "0") int start,
                                      @RequestParam(value="limit", required = false, defaultValue = "10") int limit,
                                      @RequestParam(value="sort", required = false) String sort) throws Exception {
        // logger.info("AuthorController --> Retrieve the list of authors...");
        Map<String, Object> mapClients = new HashMap<String, Object>();
        List<ClientEntity> list = new ArrayList<ClientEntity>();
        try{
            BigInteger total = BigInteger.ZERO;
            list = clientService.getAllClients();
            total = clientService.getTotalClients();

            mapClients.put("data", list);
            mapClients.put("total", total);

            return mapClients;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return mapClients;
    }

    @GetMapping("/clients/{id}")
    ClientEntity getTransaction(@PathVariable Long id) throws RecordNotFoundException {
        return clientService.getClient(id);
    }

    @PostMapping("/clients")
    ClientEntity newClient(@RequestBody ClientEntity newClient) throws RecordNotFoundException {
        return clientService.newClient(newClient);
    }

    @PutMapping("/clients/{id}")
    ClientEntity createOrUpdateClient(@RequestBody ClientEntity newClient, @PathVariable Long id) throws RecordNotFoundException {
        return clientService.createOrUpdateClient(newClient, id);
    }
}