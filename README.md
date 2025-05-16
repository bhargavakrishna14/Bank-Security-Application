## Overview

The organization requires a Bank Security Application that allows users to access banking services securely. The application provides features such as account creation, card selection, and investment options. It also includes an Admin Panel for authorized users to view user and account information.


---


## Features of the Application

The application allows users to:

* Register themselves into the system.

* Create a bank account of their choice.

* Save a Nominee for their account.

* Retrieve details for the granted Card.

* Invest money through their bank account.

* Admin users can view user and account details.


---


## Tech Stack

* Java 21

* Spring Boot 3.4.5

* Hibernate (JPA)

* Spring Security (JWT Authentication)

* MySQL Database

* Postman (API Testing)


---


## Database Schema & Entities

1. Enums:
   * AccountType
   * BranchType
   * CardType
   * InvestmentType

2. Entity Classes:
   * Account (Attributes: ID, Type, Status, Balance, Interest Rate, Branch, Proof, Opening Date, Number, Nominee, Card, User)
   * Card (Attributes: ID, Number, Holder Name, Type, Daily Limit, CVV, Allocation & Expiry Date, PIN, Status)
   * Investment (Attributes: ID, Type, Risk, Amount, Returns, Duration, Company Name, User)
   * Nominee (Attributes: ID, Relation, Name, Account Number, Gender, Age)
   * Role (Attributes: ID, Role Name)
   * User (Attributes: ID, Name, Username, Password, Address, Contact Number, Identity Proof, Roles, Accounts, Investments)

3. DTO Classes:
   * AccountDto, AdminDto, CardDto, InvestmentDto, KycDto, NomineeDto, UserDto

4. Repository Interfaces:
   * AccountRepository (findByAccountNumber, findAllActiveAccounts, etc.)
   * CardRepository (findByCardNumber)
   * InvestmentRepository
   * NomineeRepository
   * UserRepository (findByUsername)


---


## Security Implementation

JWT Authentication for secure access:

* JwtRequest and JwtResponse DTOs.

* JwtAuthenticationFilter & JwtAuthenticationHelper for request validation.

* CustomUserDetailService to fetch user details for authentication.

* BCryptPasswordEncoder for password encryption.


---


## API Endpoints

### User Endpoints

| Method       | Endpoint       | Description                                  |
| ------------ |:---------------|:---------------------------------------------|
| POST         | /user/login    | Logs in the user and returns a JWT token     |
| POST         | /user/register | Registers a new user with the role CUSTOMER  |


### UserAccount Endpoints

| Method       | Endpoint                           | Description                     |
| ------------ |:-----------------------------------|:--------------------------------|
| POST         | /account/create/{userId}           | Creates a new bank account      |
| GET          | /account/all/{userId}              | Fetches all accounts for a user |
| GET          | /account/balance                   | Retrieves account balance       |
| GET          | /account/nominee                   | Fetches nominee details         |
| PUT          | /account/updateNominee/{accountId} | Updates nominee details         |
| GET          | /account/getKycDetails             | Fetches KYC details of a user   |
| PUT          | /account/updateKyc/{accountId}     | Updates KYC details             |
| GET          | /account/getAccount/summary        | Fetches account summary         |


### UserCard Endpoints

| Method       | Endpoint        | Description                        |
| ------------ |:----------------|:-----------------------------------|
| GET          | /card/block     | Blocks a card linked to an account |
| POST         | /card/apply/new | Applies for a new card             |
| PUT          | /card/setting   | Updates card settings (limit, PIN) |


### UserInvestment Endpoints

| Method       | Endpoint    | Description                                                |
| ------------ |:------------|:-----------------------------------------------------------|
| POST         | /invest/now | Creates an investment if the account balance is sufficient |


### Admin Endpoints

| Method       | Endpoint                               | Description                |
| ------------ |:---------------------------------------|:---------------------------|
| POST         | /admin/add                             | Registers a new admin      |
| GET          | /admin/getAllUser                      | Fetches all users          |
| GET          | /admin/getUserByName/{username}        | Fetches a user by username |
| DELETE       | /admin/deleteUser/{userId}             | Deletes a user             |
| PUT          | /admin/account/deactivate              | Deactivates an account     |
| PUT          | /admin/account/activate                | Activates an account       |
| GET          | /admin/account/getActiveAccountsList   | Fetches active accounts    |
| GET          | /admin/account/getInActiveAccountsList | Fetches inactive accounts  |
| GET          | /admin/account/getAccountsByType       | Fetches accounts by type   |
| GET          | /admin/account/getAccountsByBranch     | Fetches accounts by branch |
