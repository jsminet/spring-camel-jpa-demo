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