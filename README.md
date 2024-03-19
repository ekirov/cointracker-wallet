# Cointracker Bitcoin Wallet Application Take-Home Exercise

## Overview

The Bitcoin Wallet Application is a Java-based Spring Boot application designed to manage Bitcoin addresses, synchronize transactions, retrieve balances, and provide transaction details for each address. It utilizes external APIs such as Blockchain.com to query blockchain data.


## Features

- Manage Bitcoin Addresses: Add and remove Bitcoin addresses.
- Synchronize Transactions: Automatically and manually synchronize transactions.
- Retrieve Balances: Get the current balance for a specific Bitcoin address.
- Transaction Details: Access detailed transaction history.


## Getting Started

### Prerequisites

- Java JDK 11 or later
- Maven

### Installation

1. Clone the repository:
   ```bash
   git clone https://example.com/bitcoin-wallet-app.git
   cd bitcoin-wallet-app

2. Build the project:
   ```bash
   mvn clean install

3. Configure the application:
Edit `src/main/resources/application.properties` to set your database connection and other configurations:
   ```properties
   spring.datasource.url= YourDatabaseURL
   spring.datasource.username= YourDatabaseUsername
   spring.datasource.password= YourDatabasePassword

4. Run the application:
   ```bash
   mvn spring-boot:run

The application will start and be accessible at `http://localhost:8080`.




### API Endpoints
| HTTP Verbs | Endpoints | Action | Payload |
| --- | --- | --- | --- |
| GET | /api/bitcoin-wallet | Retrieve all tracked Bitcoin addresses. |
| POST | /api/bitcoin-wallet/address/add | Add a new Bitcoin address. | Payload example: {"address":"1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"}
| DELETE | /api/bitcoin-wallet/address/{address}/remove | Remove an existing Bitcoin address. |
| GET | /api/bitcoin-wallet/address/{address}/transactions | Get transactions for a specific address. |
| GET | /api/bitcoin-wallet/address/{address}/balance | Get the current balance for a specific address. |
| POST | /api/bitcoin-wallet/synchronize | Manually trigger transaction synchronization. |
# Cointracker Bitcoin Wallet Application Take-Home Exercise

## Overview

The Bitcoin Wallet Application is a Java-based Spring Boot application designed to manage Bitcoin addresses, synchronize transactions, retrieve balances, and provide transaction details for each address. It utilizes external APIs such as Blockchain.com to query blockchain data.


## Features

- Manage Bitcoin Addresses: Add and remove Bitcoin addresses.
- Synchronize Transactions: Automatically and manually synchronize transactions.
- Retrieve Balances: Get the current balance for a specific Bitcoin address.
- Transaction Details: Access detailed transaction history.


## Getting Started

### Prerequisites

- Java JDK
- Maven
- PostgreSql

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/ekirov/cointracker-wallet.git
   cd cointracker-takehome

2. Build the project:
   ```bash
   mvn clean install

3. Configure the application:
Edit `src/main/resources/application.properties` to set your database connection and other configurations:
   ```properties
   spring.datasource.url= YourDatabaseURL
   spring.datasource.username= YourDatabaseUsername
   spring.datasource.password= YourDatabasePassword
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.hibernate.ddl-auto=update

4. Run the application:
   ```bash
   mvn spring-boot:run

The application will start and be accessible at `http://localhost:8080`.




### API Endpoints
| HTTP Verbs | Endpoints | Action | Payload |
| --- | --- | --- | --- |
| GET | /api/bitcoin-wallet | Retrieve all tracked Bitcoin addresses. |
| POST | /api/bitcoin-wallet/address/add | Add a new Bitcoin address. | Payload example: {"address":"1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"}
| DELETE | /api/bitcoin-wallet/address/{address}/remove | Remove an existing Bitcoin address. |
| GET | /api/bitcoin-wallet/address/{address}/transactions | Get transactions for a specific address. |
| GET | /api/bitcoin-wallet/address/{address}/balance | Get the current balance for a specific address. |
| POST | /api/bitcoin-wallet/synchronize | Manually trigger transaction synchronization. |

### External API Endpoint Used
Resource: https://www.blockchain.com/explorer/api/blockchain_api

API: https://blockchain.info/rawaddr/$bitcoin_address