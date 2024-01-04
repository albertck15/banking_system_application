# Banking System Application

This project imitates a banking account handler application.
I started this app because I think systems like this are in our every day, and even if it is a simple system, it is very important to make them 
maintainable and easy to use.
As crypto will rise as a payment (or just the idea itself of going to a cashless society), it will be more and more important to have stable, clean, and secure
systems. 

## Setting up the application

1. Create your MySQL database
   
I have included the script files to set up the database. 
Start with executing the *'albert_banking_system_user.sql'* file, then execute the 
*'albert_banking_system_balance.sql'*, and then execute the *'albert_banking_system_transaction.sql'* scripts.
## What this application can do?

2. Check connection and properties

As you can see in the Main's code at the 56th line, I am using a database called "albert_banking_system" on the localhost::3306
If you've set up a database with a different name, make sure to change it, and include your user and password in the _*resources/connection.properties*_ file.

3. Run the application with Main() and Enjoy!

## What this application can do?

- Log in or register a new account.
- Make a deposit.
- Make a withdraw.
- Make a transaction
- Checking balance.
- Checking transaction history(Currently not available, I am working on it).
