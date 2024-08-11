package com.moneyforward.expensetracker.driver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneyforward.expensetracker.service.CSVTransactionsParserService;
import com.moneyforward.expensetracker.utility.ValidationHelper;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpenseTrackerMain {
    public static void main(String[] args) {
        System.out.println(
                "Hi, Welcome to Expense Tracker, It is designed for personal use and generates a history of all deposits and withdrawals\n" +
                        "for a specified period.");
        try {
            //Make the program wait for input
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        handleCommandLineInputAndPrintResponse();
    }

    private static void handleCommandLineInputAndPrintResponse(){
        String continueProgram = "";
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("\nWhat specific period are you looking for the deposit and withdrawal statement for?." +
                    "\n Please specify in YYYYMM format \n");
            String periodRange = sc.nextLine();
            if(!ValidationHelper.isValidDateRange(periodRange)) {
                System.out.println("\nIt seems you have entered an invalid period." +
                        "\n Please try again and specify period in YYYYMM format. \n");
                break;
            } else {
                periodRange = periodRange.substring(0, 4) + "/" + periodRange.substring(4);
            }
            System.out.println("\nPlease give the csv file name with absolute path to extract details from? \n");
            String userInputFile = sc.nextLine();
            if(!ValidationHelper.isValidFilePath(userInputFile)){
                System.out.println("\nIt seems you have entered an invalid file path." +
                        "\n Please try again and specify a valid path to a csv file. \n");
                break;
            } else {
                System.out.println("\nPlease specify the file path where you want output to be logged, leave blank for printing output on console? \n");
                String userOutputFile = sc.nextLine();
                String jsonResponse =  getJSONResponseFromInput(periodRange, userInputFile, userOutputFile);;
                System.out.println(jsonResponse);
            }
            System.out.println("\n Do you want to continue(Y/N) \n");
            continueProgram = sc.nextLine();
        } while(continueProgram.equalsIgnoreCase("y"));
        System.out.println("Exiting !! Have a nice day.");
        sc.close();
    }


    private static String getJSONResponseFromInput(String periodRange, String userInputFile, String outputFilePath){
        String response = "";
        if(!periodRange.isEmpty() && !userInputFile.isEmpty() && !outputFilePath.isEmpty()){
            CSVTransactionsParserService service = new CSVTransactionsParserService();
            response = service.getOutputLedgerForPeriodAndWriteToFile(periodRange,userInputFile,outputFilePath);
        } else if(!periodRange.isEmpty() && !userInputFile.isEmpty()){
            CSVTransactionsParserService service = new CSVTransactionsParserService();
            response = service.getOutputLedgerForPeriod(periodRange,userInputFile);
        } else {
            System.out.println("Invalid input: Make sure your input is as prescribed.");
        }
        ObjectMapper obj = new ObjectMapper();
        try {
           return obj.writerWithDefaultPrettyPrinter().writeValueAsString(obj.readTree(response));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}