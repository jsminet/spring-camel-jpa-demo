# Spring-boot-camel-jpa-demo

## 1. Introduction
A demo project using :
- Spring Boot: Rest api using relationships between tables (ManyToOne, OneToMany, ManyToMany), and Rest verbs (GET, POST, PUT and DELETE).
- JPA: For the persistence layer.
- Camel: Camel Route consuming a csv file and persisting its content to database using JPA.

### Use-Case:
Build service that:

- Builds the file path from where data is read from
- Reads data from the CSV file
- Converts CSV records into POJO
- Transforms data with custom code into POJO
- Stores the final results in a database

## 2. Versions used:
- Spring Boot: 2.3.0.RELEASE
- Apache Camel: 3.0.0-M1
- Camel-bindy: 3.0.0-M1
- Camel-jpa: 3.0.0-M1
- Maven: 3.0+
- Java: 1.8

## 3. Installation
1. Open Git Bash.
2. Change the current working directory to the location where you want the cloned directory.
3. Type the command below to clone the repository:
```commandline
git clone https://github.com/drioumo/spring-camel-jpa-demo
```

Start it up with:
```commandline
mvn clean spring-boot:run
```

## 4. Database Installation
### 4.1 DBML
```text
// 1. Tables and References

// Creating tables
Table clients as C {
  client_id int [pk, increment] // auto-increment
  first_name varchar
  last_name varchar
  date_of_birth date
}

Table users as U{
  user_id int [pk, increment] // auto-increment
  first_name varchar 
  last_name varchar
  trigram varchar
}

Table transactions as T{
  transaction_id int [pk, increment] // auto-increment
  stock varchar
  type varchar
  price numeric
  transaction_date timestamp
  quantity int
  client_id int 
  user_id int
}

Table user_client as UC {
  user_client_id int [pk, increment] // auto-increment
  client_id int 
  user_id int
}

// 2. Creating references
Ref: T.user_id > U.user_id // many-to-one
Ref: T.client_id > C.client_id // many-to-one
Ref: UC.client_id > C.client_id // many-to-one
Ref: UC.user_id > U.user_id // many-to-one
```
See the above dbml doc [visualized on dbdiagam](https://dbdiagram.io/d/5f3e9875cf48a141ff559889)
### 4.2 Create Database and Tables
```postgres-psql
CREATE DATABASE "spring-camel-demo"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English_United States.1252'
       LC_CTYPE = 'English_United States.1252'
       CONNECTION LIMIT = -1;

CREATE TABLE clients
(
    client_id     SERIAL PRIMARY KEY,
    first_name     varchar(80) NOT NULL,
    last_name 	  varchar(80) NOT NULL,
    date_of_birth date 
);
CREATE TABLE users
(
    user_id       SERIAL PRIMARY KEY,
    first_name    varchar(80) NOT NULL,
    last_name 	  varchar(80) NOT NULL,
    trigram       varchar(80) NOT NULL
);
CREATE TABLE transactions
(
    transaction_id   SERIAL PRIMARY KEY,
    stock            varchar(80) NOT NULL,
	type             varchar(80) NOT NULL,
    price 	     numeric,
    transaction_date timestamp,
    quantity         integer,
    client_id        integer NOT NULL REFERENCES clients (client_id),
    user_id          integer NOT NULL REFERENCES users (user_id)
);
CREATE TABLE user_client
(
    user_client_id   SERIAL PRIMARY KEY,
    user_id          integer NOT NULL REFERENCES users (user_id),
    client_id        integer NOT NULL REFERENCES clients (client_id)
);
```
### 4.3 Insert Data
```postgres-psql
-- INSERT clients...
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Thomas', 'Crane', '01/01/1979');
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Alden', 'Cantrell', '01/05/1954');
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Cierra', 'Vega', '01/04/1972');
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Miranda', 'Shaffer', '02/15/1963');
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Bradyn', 'Kramer', '09/13/1980');
INSERT INTO public.clients(first_name, last_name, date_of_birth)
    VALUES ('Alvaro', 'Mcgee', '01/04/1973');	
-- INSERT users...
INSERT INTO public.users(first_name, last_name, trigram)
    VALUES ('Mounir', 'DRIOUCHI', 'MD3431'); 
INSERT INTO public.users(first_name, last_name, trigram)
    VALUES ('Philippe', 'Istas', 'PBI'); 
INSERT INTO public.users(first_name, last_name, trigram)
    VALUES ('Gregory', 'Delespes', 'AUDGED'); 
INSERT INTO public.users(first_name, last_name, trigram)
    VALUES ('Philippe', 'Foguenne', 'NRBPHF');     
                  
-- Insert user_client
INSERT INTO public.user_client(user_id, client_id)
    VALUES (1, 1);
INSERT INTO public.user_client(user_id, client_id)
    VALUES (1, 2);
INSERT INTO public.user_client(user_id, client_id)
    VALUES (2, 3);
INSERT INTO public.user_client(user_id, client_id)
    VALUES (2, 4); 
INSERT INTO public.user_client(user_id, client_id)
    VALUES (3, 1);
INSERT INTO public.user_client(user_id, client_id)
    VALUES (3, 4);           
INSERT INTO public.user_client(user_id, client_id)
    VALUES (4, 2);           
INSERT INTO public.user_client(user_id, client_id)
    VALUES (4, 4);
```

## 5. Rest API 

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

## 6. Camel Route

- Create a new file containing the transactions to be pushed into DB
```textmate
1,2,TSLA,Sell,800,1000
2,1,AMZN,Buy,400,500
2,2,MSFT,Buy,200,250
```
Below are a sequence of steps that highlight the camel process:

- Unmarshall the CSV input file using a Transaction CSV Item 
```.unmarshal(bindy)```. 
- Split the result into a list of transactions:
    ```
    .split(body()) 
    .streaming()
   ``` 
- Convert the CSV Item into a JPA entity using 
```.bean(mapper, "convertAndTransform")``` 
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
        // log.info("Converting ({}) into ({})", csvRecord, transaction);
        return transaction;
    }
}
```

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

