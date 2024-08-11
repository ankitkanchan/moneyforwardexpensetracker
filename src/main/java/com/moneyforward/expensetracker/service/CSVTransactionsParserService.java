package com.moneyforward.expensetracker.service;

import com.moneyforward.expensetracker.model.CSVTransactionsParserModel;
import com.moneyforward.expensetracker.utility.ValidationHelper;

public class CSVTransactionsParserService {

    public String getOutputLedgerForPeriod(String periodRange, String csvInputFileName){
        if(!ValidationHelper.isValidFilePath(csvInputFileName)){
            System.out.println("\nIt seems you have entered an invalid file path." +
                    "\n Please try again and specify a valid path to a csv file. \n");
            return "";
        }

       String outputLedgerJsonReponse = CSVTransactionsParserModel.processFileAndGetLedger(periodRange, csvInputFileName);
       if (outputLedgerJsonReponse == null || outputLedgerJsonReponse.isEmpty()) {
           outputLedgerJsonReponse = "";
       }
       return outputLedgerJsonReponse;
    }

    public String getOutputLedgerForPeriodAndWriteToFile(String periodRange, String csvInputFileName, String outputFilePath){
        String outputLedgerJsonReponse = getOutputLedgerForPeriod(periodRange, csvInputFileName);
        if (outputLedgerJsonReponse == null || outputLedgerJsonReponse.isEmpty()) {
            outputLedgerJsonReponse = "";
        }
        if(!outputLedgerJsonReponse.isEmpty())
            CSVTransactionsParserModel.writeToFilePath(outputLedgerJsonReponse,outputFilePath);
        return outputLedgerJsonReponse;
    }
}
