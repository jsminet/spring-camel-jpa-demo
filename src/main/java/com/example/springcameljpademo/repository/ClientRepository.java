package com.example.springcameljpademo.repository;

import com.example.springcameljpademo.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by MD3431 on 03/08/2020
 */
@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
