package com.nikitagru.search.read;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {
    private BufferedReader reader;
    private Pattern punctuationMark = Pattern.compile("^[-.?!)(,:^]+$");

    public Reader() {
        InputStream in = getClass().getResourceAsStream("/airports.dat");

        reader = new BufferedReader( new InputStreamReader(in));
    }

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
