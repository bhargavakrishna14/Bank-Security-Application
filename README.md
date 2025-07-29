## Overview

A Spring Boot application implementing **JWT-based authentication**, **role-based access control**, and various banking operations for **customers** and **admins**.

---


## Features of the Application

### Authentication & Security
* JWT Authentication for all endpoints

* Role-based Access Control:
  * CUSTOMER: Limited to their own accounts & cards
  * ADMIN: Access to admin endpoints

* BCrypt Password Encryption

* Spring Security filters for JWT validation

### Banking Operations
* Account Management: Create, KYC update, nominee update, balance inquiry

* Card Management: Apply, modify settings, block cards

* Investment Operations: Invest in different plans

* Admin Controls: Manage users, accounts, and perform bulk queries

### Developer-Oriented
* Layered Architecture (Controller → Service → Repository)

* DTO-based Request/Response Models (no direct entity exposure)

* Global Exception Handling for meaningful API errors

* Ready for Swagger/OpenAPI integration

---


## Tech Stack

* Language: Java 21

* Framework: Spring Boot 3.4.5

* ORM: Hibernate / JPA

* Security: Spring Security with JWT

* Database: MySQL (default), H2 (dev)

* Build Tool: Maven

* API Testing: Postman

---


## Database Schema & Entities

1. Enums:
   * AccountType
   * BranchType
   * CardType
   * InvestmentType
   * AccountStatus
   * CardStatus

2. Entities:
   * Account (Attributes: ID, Type, Status, Balance, Interest Rate, Branch, Proof, Opening Date, Number, Nominee, Card, User)
   * Card (Attributes: ID, Number, Holder Name, Type, Daily Limit, CVV, Allocation & Expiry Date, PIN, Status)
   * Investment (Attributes: ID, Type, Risk, Amount, Returns, Duration, Company Name, User)
   * Nominee (Attributes: ID, Relation, Name, Account Number, Gender, Age)
   * Role (Attributes: ID, Role Name)
   * User (Attributes: ID, Name, Username, Password, Address, Contact Number, Identity Proof, Roles, Accounts, Investments)

3. DTO Classes:
   * AccountDto, CardDto, InvestmentDto, KycDto, NomineeDto

4. Repositories:
   * AccountRepository (findByAccountNumber, findAllActiveAccounts, etc.)
   * CardRepository (findByCardNumber)
   * InvestmentRepository
   * NomineeRepository
   * UserRepository (findByUsername)

---

## Authentication Flow

* Register - POST /api/v1/auth/register
```
{
  "username": "john_doe",
  "password": "securePass123",
  "email": "john@example.com",
  "role": "CUSTOMER"
}
```

* Login - POST /api/v1/auth/login
```
{
  "username": "john_doe",
  "password": "securePass123"
}
```
Response:
```
{ "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }
```

Use this token in Authorization → Bearer Token for subsequent requests.

---


## API Endpoints

### Authentication (Public)

| Method       | Endpoint              | Description           |
| ------------ |:----------------------|:----------------------|
| POST         | /api/v1/auth/register | Register a new user   |
| POST         | /api/v1/auth/login    | Login & get JWT token |


### Account Management (CUSTOMER)

| Method       | Endpoint                                            | Description                   |
| ------------ |:----------------------------------------------------|:------------------------------|
| POST         | /api/v1/account/create/{userId}                     | Create a new bank account     |
| GET          | /api/v1/account/all/{userId}                        | Get all accounts              |
| GET          | /api/v1/account/balance?accountNumber={}            | Get account balance           |
| GET          | /api/v1/account/nominee?accountNumber={}            | Get nominee details           |
| PUT          | /api/v1/account/updateNominee/{accountId}           | Update nominee details        |
| GET          | /api/v1/account/getKycDetails?accountNumber={}      | Get KYC details of a user     |
| PUT          | /api/v1/account/updateKyc/{accountId}               | Update KYC details            |
| GET          | /api/v1/account/getAccount/summary?accountNumber={} | Account summary               |


### Card Operations (CUSTOMER)

| Method       | Endpoint                                          | Description                       |
| ------------ |:--------------------------------------------------|:----------------------------------|
| POST         | /api/v1/card/apply/new?accountNumber={}           | Apply for a new card              |
| PUT          | /api/v1/card/setting?cardNumber={}                | Update card settings (limit, PIN) |
| GET          | /api/v1/card/block?accountNumber={}&cardNumber={} | Block card linked to an account   |


### Investments (CUSTOMER)

| Method       | Endpoint                        | Description                                                |
| ------------ |:--------------------------------|:-----------------------------------------------------------|
| POST         | /api/v1/invest/now?accountId={} | Make a new investment if the account balance is sufficient |


### Admin Endpoints (ADMIN ONLY)

| Method       | Endpoint                                                | Description            |
| ------------ |:--------------------------------------------------------|:-----------------------|
| GET          | /api/v1/admin/getAllUser?page={}&size={}                | Get all users          |
| GET          | /api/v1/admin/getUserByName/{username}                  | Get user by username   |
| DELETE       | /api/v1/admin/deleteUser/{userId}                       | Delete user            |
| PUT          | /api/v1/admin/account/deactivate?userId={}&accountId={} | Deactivate an account  |
| PUT          | /api/v1/admin/account/activate?userId={}&accountId={}   | Activate an account    |
| GET          | /api/v1/admin/account/getActiveAccountsList             | List active accounts   |
| GET          | /api/v1/admin/account/getInActiveAccountsList           | List inactive accounts |
| GET          | /api/v1/admin/account/getAccountsByType                 | Get accounts by type   |
| GET          | /api/v1/admin/account/getAccountsByBranch               | Get accounts by branch |

---

## Future Enhancements

* Swagger/OpenAPI documentation
  
* Email/SMS notifications for critical operations
  
* Scheduled transactions & recurring investments

---
