package com.moneyforward.expensetracker.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneyforward.expensetracker.entities.OutputLedger;
import com.moneyforward.expensetracker.entities.Transaction;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CSVTransactionsParserModel {
   /**
   * Method containing logic to process CSV and do data sanity check inside the file
   * if sanity check passes, it then proceeds to create a response JSON object
   * */
   public static String processFileAndGetLedger(String periodRange, String csvFileName){
       try {
           CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFileName)).withSkipLines(1).build(); //Skipping the header
           List<String[]> rowsInCSV = csvReader.readAll();
           if(rowsInCSV.stream().allMatch(row ->isRowValidForData(row)==true)) {

               //Get All Transactions in a periodRange
               List<Transaction> transactionListForThePeriod = getTransactionsInPeriodRange(periodRange, rowsInCSV);

               //Sort the transactions in descending order by date
               List<Transaction> transactionListSortedByDate = getTransactionListSortedByDate(transactionListForThePeriod);
               Collections.reverse(transactionListSortedByDate);

               //Get Total Income in a periodRange
               Integer totalIncomeInAPeriod = getTotalIncomeInAPeriod(transactionListSortedByDate);

               //Get Total Expenses in a periodRange
               Integer totalExpenditureInAPeriod = getTotalExpenditureInAPeriod(transactionListSortedByDate);

               OutputLedger outputLedgerObject = new OutputLedger(periodRange, totalIncomeInAPeriod, totalExpenditureInAPeriod, transactionListSortedByDate);
               ObjectMapper obj = new ObjectMapper();
               String objectLedgerJsonString = obj.writeValueAsString(outputLedgerObject);
               return objectLedgerJsonString;
           }
       } catch (IOException e) {
           System.out.println("There seems to be some problem parsing your input.Please make sure that details are valid");
           throw new RuntimeException(e);
       }
       return "";
   }

    private static boolean isRowValidForData(String[] row){
       if(row.length!=3){
           return false;
       } else if(!isValidDate(row[0])){
           return false;
       }
       return true;
    }

    private static boolean isValidDate(String dateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            // Parse the date string
            LocalDate date = LocalDate.parse(dateStr, formatter);
            if(date!=null)
                return true;
        } catch (DateTimeParseException e) {
            // If parsing fails, the date is not valid
            return false;
        }
        return false;
    }

    private static Integer getTotalExpenditureInAPeriod(List<Transaction> transactionListSortedByDateDesc) {
        return transactionListSortedByDateDesc.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .mapToInt(transaction -> transaction.getAmount()).sum();
    }

    private static Integer getTotalIncomeInAPeriod(List<Transaction> transactionListSortedByDateDesc) {
        return transactionListSortedByDateDesc.stream()
                .filter(transaction -> transaction.getAmount() >= 0)
                .mapToInt(transaction -> transaction.getAmount()).sum();
    }

    private static List<Transaction> getTransactionListSortedByDate(List<Transaction> transactionListForThePeriod) {
        return transactionListForThePeriod.stream().sorted(Comparator.comparing(
                        source -> LocalDate.parse(source.getDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd"))))
                .collect(Collectors.toList());
    }

    private static List<Transaction> getTransactionsInPeriodRange(String periodRange, List<String[]> rows) {
        return rows.stream().filter(x -> x[0].contains(periodRange))
                .map(x -> new Transaction(x[0], Integer.parseInt(x[1]), x[2])).collect(Collectors.toList());
    }

    public static void writeToFilePath(String response, String outputFilePath){
        Path filePath = Paths.get(outputFilePath);
        try {
            // Write lines of text to the file
            Files.write(filePath,response.getBytes(StandardCharsets.UTF_8));
            System.out.println("File written successfully to " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("File writing failed");
            throw new RuntimeException(e.getMessage());
        }
    }

}
