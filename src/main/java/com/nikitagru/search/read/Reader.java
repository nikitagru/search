package com.nikitagru.search.read;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Класс считывания данных
 */
public class Reader {
    private BufferedReader reader;
    // состоит ли вся строка из знаков препинания
    private Pattern punctuationMark = Pattern.compile("^[-.?!)(,:^]+$");

    public Reader() {
        InputStream in = getClass().getResourceAsStream("/airports.dat");

        reader = new BufferedReader( new InputStreamReader(in));
    }

    /***
     * Считывание ввода пользователя
     * @return ввод пользователя
     */
    public String consoleReader() {
        Scanner in = new Scanner(System.in);
        String result = in.nextLine().toLowerCase(Locale.ROOT).replaceAll(" ", "");

        Matcher matcher = punctuationMark.matcher(result);

        if (result.equals("") || matcher.find()) {
            System.out.println("Вы ввели некорректное значение. Попробуйте снова \n");
            consoleReader();
        }

        return result;
    }

    /***
     * Получение строки из файла
     * @return строка с данными аэропорта
     */
    public String getLine() {
        String line = null;

        try {
            line = reader.readLine();
            if (line != null) {
                line = line.toLowerCase(Locale.ROOT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }
}
