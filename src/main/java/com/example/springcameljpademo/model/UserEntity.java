package com.example.springcameljpademo.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by MD3431 on 03/08/2020
 * users entity
 *      user_id serial NOT NULL,
 *      first_name character varying(80) NOT NULL,
 *      last_name character varying(80) NOT NULL,
 *      trigram character varying(80) NOT NULL
 */
@Entity
@JsonAutoDetect
@SequenceGenerator(name = "userSequence", sequenceName = "users_user_id_seq", allocationSize = 1)
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    // @JsonIgnore
    @Column(name = "user_id")
    private Long userId;

    @Column(name="first_name", length=80, nullable=false)
    private String firstName;

    @Column(name="last_name", length=80, nullable=false)
    private String lastName;

    @Column(name="trigram", length=80, nullable=false)
    private String trigram;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user","client"})
    @JsonProperty("transactions")
    private List<TransactionEntity> transactions;

    @ManyToMany(mappedBy = "users")
    @JsonIgnoreProperties({"users", "transactions"})
    private List<ClientEntity> clients;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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
    public String getTrigram() {
        return trigram;
    }
    public void setTrigram(String trigram) {
        this.trigram = trigram;
    }
    public List<TransactionEntity> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }
    public List<ClientEntity> getClients() {
        return clients;
    }
    public void setClients(List<ClientEntity> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(userId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", Trigram='").append(trigram).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
