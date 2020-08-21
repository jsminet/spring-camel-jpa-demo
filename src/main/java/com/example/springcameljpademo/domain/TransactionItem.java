package com.example.springcameljpademo.domain;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;

/**
 * Created by MD3431 on 06/08/2020.
 */
public class TransactionItem {

    private Long transactionId;
    private Long userId;
    private Long clientId;
    private String stock;
    private String type;
    private BigDecimal price;
    private Integer quantity;

    public Long getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "TransactionItem {" +
                "userId= " + userId +
                ", clientId= " + clientId +
                ", stock= '" + stock + '\'' +
                ", type= '" + type + '\'' +
                ", price= " + price +
                ", Quantity= " + quantity +
                '}';
    }
}
