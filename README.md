# Cointracker Bitcoin Wallet Application Take-Home Exercise

## Overview

The Bitcoin Wallet Application is a Java-based Spring Boot application designed to manage Bitcoin addresses, synchronize transactions, retrieve balances, and provide transaction details for each address. It utilizes external APIs such as Blockchain.com to query blockchain data.


## Features

- Manage Bitcoin Addresses: Add and remove Bitcoin addresses.
- Synchronize Transactions: Automatically and manually synchronize transactions.
- Retrieve Balances: Get the current balance for a specific Bitcoin address.
- Transaction Details: Access detailed transaction history.

## Assumptions Made
- Transactions will only be updated periodically and/or manually via the Blockchain api.
- Retrieving balances and transactions will display them for individual addresses only in the wallet.
- This is a prototype, so not all features are fully implemented. For example
- - I do not take into account the fact that the blockchain api only returns by default a set amount of transactions. 
- - I assume that deletes will be hard-deletes and not soft-deletes.
- - I have not implemented advanced system design such as caching, load-balancing, or containers considering the time restrictions and prototype nature.
- - My main skills include api specifications, backend application services, and db integration, so I did not create a UI.


## Architecture Decisions
- UI: I am mainly focused on the backend, but I use Postman to interact with the backend through the API calls.
- API Layer: Spring Boot Framework is the central node to handle the API requests/responses. My endpoints are restful and defined for managing all necessary requirements.
- Service Layer: Implements the business logic for all the endpoints, including a specific service for communicating with the Blockchain.com api to fetch real time data
- Data Layer: Data is stored locally in a PostreSql database with two tables representing the addresses in the wallet and the transactions associated with the addresses. Repositories facilitate communication between the application server and DB.
- Scheduling and Background tasks: It is implemented, but commented out to run periodically if needed using Spring's @Scheduled annotation.
- Unit tests: There are unit tests for the controller to show that testing can be done on a stricter basis.

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