package com.example.springcameljpademo.domain;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;

/**
 * Created by MD3431 on 06/08/2020.
 */
@CsvRecord(separator = ",", skipFirstLine = false)
public class TransactionCsvItem {

    @DataField(pos = 1)
    private Long userId;

    @DataField(pos = 2)
    private Long clientId;

    @DataField(pos = 3)
    private String stock;

    @DataField(pos = 4)
    private String type;

    @DataField(pos = 5, precision = 2)
    private BigDecimal price;

    @DataField(pos = 6)
    private Integer quantity;

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
        return "Item{" +
                "userId= " + userId +
                ", clientId= " + clientId +
                ", stock= '" + stock + '\'' +
                ", transactionType= '" + type + '\'' +
                ", price= '" + price +
                ", Quantity= " + quantity +
                '}';
    }
}
