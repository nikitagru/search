package com.nikitagru.search.search;

import com.nikitagru.search.read.Reader;

import java.util.TreeSet;

/***
 * Класс поиска строки
 */
public class Searcher {
    // Колонка для поиска (отсчет идет с 1)
    private int searchColumn;

    public Searcher(int searchColumn) {
        this.searchColumn = searchColumn;
    }

    /**
     * Выводит на консоль всех найденных Аэропортах и информацию о них
     */
    public void showAirports() {
        TreeSet<String> resultLines = new TreeSet<>();

        Reader reader = new Reader();
        String userInput = reader.consoleReader();
        long start = System.currentTimeMillis();


        int [] prefixArr = createPrefixArr(userInput);

        String line = reader.getLine();

        while (line != null) {
            String columnValue = getColumnValue(line);
            if (isMatch(columnValue, userInput, prefixArr)) {
                String resultLine = line.substring(line.indexOf(",") + 1);
                resultLines.add(resultLine);
            }
            line = reader.getLine();
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        printResult(resultLines);
        System.out.println("Затраченное время: " + timeElapsed + "мс");
    }

    private void printResult(TreeSet<String> resultLines) {
        StringBuffer result = new StringBuffer();

        for (String line : resultLines) {
            result.append(line + "\n");
        }
        System.out.println("Найденно строк: " + resultLines.size());
        System.out.println(result);
    }

    /***
     * Поиск совпадений со строкой пользователя
     * @param line строка из файла
     * @param userInput ввод пользователя
     * @param prefixArr массив префиксов
     * @return true если найденно совпадение
     */
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

    /***
     * Создание массива префиксов
     * @param userInput ввод пользователя
     * @return массив префиксов
     */
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

    /***
     * Возвращает данные из колонки
     * @param line строка из файла
     * @return данные из колонки
     */
    private String getColumnValue(String line) {
        return line.split(",")[searchColumn - 1];
    }
}
