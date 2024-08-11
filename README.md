# moneyforwardexpensetracker
Expense Tracking Program developed as part of moneyforward technical hiring assignment. 

Based on transaction data present in a csv file, this program helps in generatin a history of all deposits and withdrawals for a specified month. 

#Contents
==========
Packages and Java Classes Descriptions: 

- com.moneyforward.expensetracker.driver.ExpenseTrackerMain - Main Class to execute the program as a Java application.
- com.moneyforward.expensetracker.service.CSVTransactionParserService - Service class used by Driver class to invoke model layer.
- com.moneyforward.expensetracker.model.CSVTransactionParserModel - Model class containing the business logic for parsing csv inputs and generating JSON responses.
- com.moneyforward.expensetracker.entities.OutputLedger - Java representation of the response JSON object.
- com.moneyforward.expensetracker.entities.Transaction - Java representation of the transaction JSON object present in the response.
- com.moneyforward.expensetracker.utility.ValidationHelper -  Utitlity class containing methods used for input validation.

#Tests
========
Unit Tests are present in src/main/test/java/com/moneyforward/expensetracker/service/tests

#Dependencies
==========
This project requires maven and JDK1.8+ to build and compile.

#Execution
===========
Program accepts the following two inputs:
A. Year and Month. YYYYMM Format.
B. Path to the file that records the history of wallet deposits and withdrawals. This should be absolute path.

The program interface is user intuitive and user can enter each input step by step, one at a time.

