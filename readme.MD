# spring-boot-camel-jpa-demo

## 1. Introduction
A demo project using :
- Spring Boot: Rest api using relationships between tables (ManyToOne, OneToMany, ManyToMany), and Rest verbs (GET, POST, PUT and DELETE).
- JPA: For the persistence layer.
- Camel: Camel Route consuming a csv file and persisting its content to database using JPA.

## 2. Versions used:
- Spring Boot: 2.3.0.RELEASE
- Apache Camel: 3.0.0-M1
- Camel-bindy: 3.0.0-M1
- Camel-jpa: 3.0.0-M1
- Postgres Docker image (9.6.19-alpine)
- Maven: 3.0+
- Java: 1.8

## 3. Installation
## 3.1. Java Installation

1. Open Git Bash.
2. Change the current working directory to the location where you want the cloned directory.
3. Type the command below to clone the repository:
```commandline
git clone https://github.com/drioumo/spring-camel-jpa-demo
```

## 3.2. Database Installation using Docker image
1. Open a terminal and run the docker command:
```commandline
docker run --name postgres-docker -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:9.6.19-alpine
```
Basically, this command:
- pulls the ```postgres:9.6.19-alpine``` from [Docker Hub](https://hub.docker.com/_/postgres). 
- names the Docker container to ```postgres-docker```
- sets the ```POSTGRES_PASSWORD``` to ```postgres``` 
- maps the container's internal ```5432``` port to external ```5432```
- and enables to run the Docker container in the background (```-d```)

2. Create tables & Insert Data:
From dbeaver, you can create the postgres connection:

![image](https://user-images.githubusercontent.com/6589406/90981257-3494a780-e560-11ea-811e-0fac8cd0608b.png)

And execute the scripts below:

![scripts](https://user-images.githubusercontent.com/6589406/90981323-9f45e300-e560-11ea-8c61-eb778925c549.png)

### 3.3. DBML
See the above dbml doc [visualized on dbdiagam](https://dbdiagram.io/d/5f3e9875cf48a141ff559889)

![spring-boot-camel-jpa](https://user-images.githubusercontent.com/6589406/90981101-80931c80-e55f-11ea-8261-e638facad38a.png)

### 3.4. Run the application
Start it up with:
```commandline
mvn clean spring-boot:run
```

## 4. Rest API 
Using Postman, you can make all the requests below to test the REST API on this base URL http://localhost:8080:

### Users
- Show List of users: ```GET /v1/demo/users ```
- Get a user: ```GET /v1/demo/users/{id} ```
- Create a user: ```POST /v1/demo/users ```
- Update a user: ```PUT /v1/demo/users/{id} ```
### Clients
- Show List of clients: ```GET /v1/demo/clients ```
- Get a client: ```GET /v1/demo/clients/{id} ```
- Create a client: ```POST /v1/demo/clients ```
- Update a client: ```PUT /v1/demo/clients/{id} ```
### Transactions
- Show List of transactions: ```GET /v1/demo/transactions ```
- Get a transaction: ```GET /v1/demo/transactions/{id} ```
- Create a transaction: ```POST /v1/demo/transactions ```
- Update a transaction: ```PUT /v1/demo/transactions/{id} ```
- Delete a transaction: ```DELETE /v1/demo/transactions/{id} ```

## 5. Camel Route

- Create a new file containing the transactions to be pushed into DB
```textmate
1,2,TSLA,Sell,800,1000
2,1,AMZN,Buy,400,500
2,2,MSFT,Buy,200,250
```
and push it to the input folder 

![input_folder](https://user-images.githubusercontent.com/6589406/90981602-47a87700-e562-11ea-8816-e556475604e8.png)

Below are a sequence of steps that highlight the camel process:

- Unmarshall the CSV input file using a Transaction CSV Item 
```.unmarshal(bindy)```. 

```java
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
```

- Split the result into a list of transactions:
    ```
    .split(body()) 
    .streaming()
   ```   
- Convert the CSV Item into a JPA entity using 
```.bean(mapper, "convertAndTransform")``` 

```java
@Component
public class CsvRecordToTransactionMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    public TransactionEntity convertAndTransform(TransactionCsvItem csvRecord) throws RecordNotFoundException {
        //TODO using Lombok builder
        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionId(null);
        transaction.setStock(csvRecord.getStock());
        transaction.setType(csvRecord.getType());
        transaction.setPrice(csvRecord.getPrice());
        transaction.setQuantity(csvRecord.getQuantity());
        transaction.setUser(userService.getUser(csvRecord.getUserId()));
        transaction.setClient(clientService.getClient(csvRecord.getClientId()));
        return transaction;
    }
}
```
- Persist the JPA entity into DB
    ```.to("jpa:com.example.springcameljpademo.model.TransactionEntity")```

```java
@Component
public class CamelRoute extends RouteBuilder {
    @Autowired
    Environment environment;

    @Autowired
    private CsvRecordToTransactionMapper mapper;

    @Override
    public void configure() throws Exception {
        DataFormat bindy = new BindyCsvDataFormat(TransactionCsvItem.class);

        from("{{fromRoute}}")
                .to("{{toRoute}}")
                .unmarshal(bindy)
                    .log("The unmarshalled object is ${body}")
                .split(body())
                .streaming()
                    .log("Record after splitting is ${body}")
                .bean(mapper, "convertAndTransform")
                    .log("Record after CSV mapping is ${body}")
                .to("jpa:com.example.springcameljpademo.model.TransactionEntity")
                    .log("Record is ${body}")
                ;
    }
}
```