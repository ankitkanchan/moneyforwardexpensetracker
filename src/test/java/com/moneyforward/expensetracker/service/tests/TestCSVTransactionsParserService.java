package com.moneyforward.expensetracker.service.tests;

import com.moneyforward.expensetracker.service.CSVTransactionsParserService;
import com.moneyforward.expensetracker.utility.ValidationHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class TestCSVTransactionsParserService {

    CSVTransactionsParserService csvTransactionsParserService;

    @Before
    public void setup()
    {
        csvTransactionsParserService = new CSVTransactionsParserService();
    }

    @Test
    public void testGetOutputLedgerForPeriod(){
        String response = csvTransactionsParserService.getOutputLedgerForPeriod("2023/06",
                "src/test/java/com/moneyforward/expensetracker/service/tests/expenseFileforTest.csv");
        assertNotNull(response);
        assertEquals("{\"period\":\"2023/06\",\"total_income\":0,\"total_expenditure\":-720,\"transactions\":[{\"date\":\"2023/06/15\",\"amount\":-720,\"content\":\"transportation\"}]}",response);
    }

    @Test
    public void testGetOutputLedgerForPeriodAndWriteToFile(){
        String response = csvTransactionsParserService.getOutputLedgerForPeriodAndWriteToFile("2023/06",
                "src/test/java/com/moneyforward/expensetracker/service/tests/expenseFileforTest.csv","src/test/java/com/moneyforward/expensetracker/service/tests/logs.txt");
        assertNotNull(response);
        assertEquals("{\"period\":\"2023/06\",\"total_income\":0,\"total_expenditure\":-720,\"transactions\":[{\"date\":\"2023/06/15\",\"amount\":-720,\"content\":\"transportation\"}]}",response);
        String fileName = Paths.get("src/test/java/com/moneyforward/expensetracker/service/tests/logs.txt").getFileName().toString();
        assertEquals("logs.txt",fileName);
    }

    @After
    public void cleanup(){
        csvTransactionsParserService = null;
    }

}
