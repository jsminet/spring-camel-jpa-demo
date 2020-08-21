package com.example.springcameljpademo.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by MD3431 on 03/08/2020
 * transactions entity
 *      transaction_id serial NOT NULL,
 *      stock character varying(80) NOT NULL,
 *      type character varying(80) NOT NULL,
 *      price numeric,
 *      transaction_date timestamp without time zone,
 *      quantity integer,
 *      client_id integer NOT NULL,
 *      user_id integer NOT NULL
 */
@Entity
@JsonAutoDetect
@SequenceGenerator(name = "transactionSequence", sequenceName = "transactions_transaction_id_seq", allocationSize = 1)
@Table(name = "transactions")
public class TransactionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    @JsonIgnore
    private Long transactionId;

    @Column(name="stock", length=80, nullable=false)
    private String stock;

    @Column(name="type", length=80, nullable=false)
    private String type;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="transaction_date")
    private Date transactionDate;

    @Column(name="quantity")
    private Integer quantity;

    @Transient
    private BigDecimal transactionAmount;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnoreProperties({"transactions", "clients"})
    @JsonProperty("user")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="client_id")
    @JsonIgnoreProperties({"transactions", "users"})
    @JsonProperty("client")
    private ClientEntity client;

    public TransactionEntity() {}

    public TransactionEntity(Long id, String stock, String type, BigDecimal price,
                             Date transactionDate, Integer quantity,
                             UserEntity user, ClientEntity client) {
        this.transactionId = transactionId;
        this.stock = stock;
        this.type = type;
        this.price = price;
        this.transactionDate = transactionDate;
        this.quantity = getQuantity();
        this.user = user;
        this.client = client;
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    public void calculateTransactionAmount() {
        transactionAmount =  price.multiply(new BigDecimal(quantity));
    }

    @PrePersist
    @PreUpdate
    public void setTransactionDate() {
        transactionDate =  new Date();
    }

    public Long getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
    public String getStock() {
        return stock;
    }
    public void setStock(String stock) {
        this.stock = stock;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Date getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }
    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
    public ClientEntity getClient() {
        return client;
    }
    public void setClient(ClientEntity client) { this.client = client; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("id=").append(transactionId);
        sb.append(", stock='").append(stock).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", quantity='").append(quantity).append('\'');
        sb.append(", transactionAmount='").append(transactionAmount).append('\'');
        sb.append('}');
        return sb.toString();
    }
}