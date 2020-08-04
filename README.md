###### **Simple "banking" application:**
* Client should be able to sign up with email & password
* Client should be able to deposit money
* Client should be able to withdraw money
* Client should be able to see the account balance and statement
--------------------------
- run Application
- application have 3 predefined users:
    - user1:pwd1
    - user2:pwd1
    - user3:pwd1
- user and password use in Basic Auth
- authorization/authentication isn't saved in session (each request should have Basic Auth)
- each User have one Account with 0 balance 
------------------------
###### **end points:**

- GET - `http://localhost:8080/v1/account`
- POST - `http://localhost:8080/v1/account/deposit`
with JSON `{
        	"amount": 45
        }`
- POST - `http://localhost:8080/v1/account/withdraw`
with JSON `{
        	"amount": 45
        }`
- GET - `http://localhost:8080/v1/account/transactions`
- GET - `http://localhost:8080/v1/account/transactions/{transactionId}`