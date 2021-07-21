package com.nikitagru.search.search;

import com.nikitagru.search.read.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.scheduling.annotation.Scheduled;

public class Searcher {
    @Value("$search.column")
    private int searchColumn;

    @Autowired
    private ApplicationArguments applicationArguments;

    public void showAirports() {
        StringBuffer result = new StringBuffer();

        Reader reader = new Reader();
        String userInput = reader.consoleReader();
        int [] prefixArr = createPrefixArr(userInput);

        String line = reader.getLine();

        while (line != null) {
            String columnValue = getColumnValue(line);
            if (isMatch(columnValue, userInput, prefixArr)) {
                String resultLine = line.substring(line.indexOf(","));
                result.append(resultLine + "\n");
            }
            line = reader.getLine();
        }
    }

    private boolean isMatch(String line, String userInput, int[] prefixArr) {
        int linePointer = 0;
        int userInputPointer = 0;

        while(linePointer != line.length()) {
            char lineWord = line.charAt(linePointer);
            char userInputWord = userInput.charAt(userInputPointer);

            if (lineWord == userInputWord) {
                if (userInputPointer == userInput.length() - 1) {
                    return true;
                }
                linePointer++;
                userInputPointer++;
            } else {
                if (userInputPointer == 0) {
                    linePointer++;
                } else {
                    userInputPointer = prefixArr[userInputPointer - 1];
                }
            }
        }

        return false;
    }

    private int[] createPrefixArr(String userInput) {
        int[] prefixArr = new int[userInput.length()];

        int j = 0;
        int i = 1;
        while (i != userInput.length()) {
            char suffix = userInput.charAt(i);
            char prefix = userInput.charAt(j);

            if (prefix != suffix) {
                if (j == 0) {
                    prefixArr[i] = 0;
                    i++;
                } else {
                    j = prefixArr[j - 1];
                }
            } else {
                prefixArr[i] = j + 1;
                j++;
                i++;
            }
        }

        return prefixArr;
    }

    private String getColumnValue(String line) {
        if (applicationArguments.getSourceArgs()[0] == null) {
            return line.split(",")[searchColumn - 1];
        }
        int column = 0;

        try {
            column = Integer.parseInt(applicationArguments.getSourceArgs()[0]);
        } catch (NumberFormatException e) {
            System.out.println("В качестве номера колонки введено не число");
            e.printStackTrace();
        }
        return line.split(",")[column];
    }

}
