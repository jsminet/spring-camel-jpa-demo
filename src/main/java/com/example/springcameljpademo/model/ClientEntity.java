package com.example.springcameljpademo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

/**
 * Created by MD3431 on 03/08/2020
 *  clients entity
 *      client_id serial NOT NULL,
 *      first_name character varying(80) NOT NULL,
 *      last_name character varying(80) NOT NULL,
 *      date_of_birth date
 */
@Entity
@JsonAutoDetect
@SequenceGenerator(name = "clientsSequence", sequenceName = "clients_client_id_seq", allocationSize = 1)
@Table(name = "clients")
public class ClientEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "client_id")
    // @JsonIgnore
    //@JsonProperty("id")
    private Long clientId;

    @Column(name="first_name", length=80, nullable=false)
    private String firstName;

    @Column(name="last_name", length=80, nullable=false)
    private String lastName;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    @OneToMany(mappedBy = "client")
    @JsonIgnoreProperties({"user","client"})
    @JsonProperty("transactions")
    private List<TransactionEntity> transactions;

    @ManyToMany
    @JsonIgnoreProperties({"clients", "transactions"})
    @JoinTable(name="user_client", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<UserEntity> users;

    @PostLoad
    @PostPersist
    @PostUpdate
    public void calculateAge(){
        if (dateOfBirth == null) {
            age = null;
            return;
        }
        age =  Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public List<TransactionEntity> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }
    public List<UserEntity> getUsers() {
        return users;
    }
    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
