package com.nikitagru.search.search;

import com.nikitagru.search.read.Reader;

public class Searcher {
    private int searchColumn;

    public Searcher(int searchColumn) {
        this.searchColumn = searchColumn;
    }

    public void showAirports() {
        StringBuffer result = new StringBuffer();

        Reader reader = new Reader();
        String userInput = reader.consoleReader();
        int [] prefixArr = createPrefixArr(userInput);

        String line = reader.getLine();

        while (line != null) {
            String columnValue = getColumnValue(line);
            if (isMatch(columnValue, userInput, prefixArr)) {
                String resultLine = line.substring(line.indexOf(",") + 1);
                result.append(resultLine + "\n");
            }
            line = reader.getLine();
        }

        System.out.println(result);
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
        return line.split(",")[searchColumn - 1];
    }
}
