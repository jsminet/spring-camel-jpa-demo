package com.example.springcameljpademo.service;

import com.example.springcameljpademo.exception.RecordNotFoundException;
import com.example.springcameljpademo.model.ClientEntity;
import com.example.springcameljpademo.model.TransactionEntity;
import com.example.springcameljpademo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by MD3431 on 03/08/2020
 */
@Service(value = "clientService")
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientEntity getClient(Long id) throws RecordNotFoundException{
        Optional<ClientEntity> client = clientRepository.findById(id);
        if(client.isPresent()) {
            return client.get();
        } else {
            throw new RecordNotFoundException("No client record exist for given id");
        }
    }

    public List<ClientEntity> getAllClients() {
        List<ClientEntity> clientList = clientRepository.findAll();
        if(clientList.size() > 0) {
            return clientList;
        } else {
            return new ArrayList<ClientEntity>();
        }
    }

    public ClientEntity newClient(ClientEntity newEntity) throws RecordNotFoundException
    {
        return clientRepository.save(newEntity);
    }

    public ClientEntity createOrUpdateClient(ClientEntity clientEntity, Long id) throws RecordNotFoundException
    {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setFirstName(clientEntity.getFirstName());
                    client.setLastName(clientEntity.getLastName());
                    client.setDateOfBirth(clientEntity.getDateOfBirth());
                    client.setTransactions(clientEntity.getTransactions());
                    client.setUsers(clientEntity.getUsers());
                    return clientRepository.save(client);
                })
                .orElseGet(() -> {
                    return clientRepository.save(clientEntity);
                });
    }

    public BigInteger getTotalClients() {
        List<ClientEntity> clientList = clientRepository.findAll();
        if(clientList.size() > 0) {
            return BigInteger.valueOf(clientList.size());
        } else {
            return BigInteger.ZERO;
        }
    }
}
