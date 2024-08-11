package com.moneyforward.expensetracker.utility;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {

    public static boolean isValidDateRange(String inputtedDate){
        String regex = "\\d{6}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputtedDate);

        if (matcher.matches()) {
            int validMonthNum = Integer.parseInt(inputtedDate.substring(4));
            if( validMonthNum >= 1 && validMonthNum <= 12 ){
                return true;
            }
        }
        return false;
    }

    public static boolean isValidFilePath(String path) {
        try {
            // Convert the string path to a Path object
            Path p = Paths.get(path);
            // Check if the path exists
            return Files.exists(p) && path.endsWith(".csv");
        } catch (InvalidPathException e) {
            // If the path string cannot be converted to a Path, it is invalid
            return false;
        }
    }
}
