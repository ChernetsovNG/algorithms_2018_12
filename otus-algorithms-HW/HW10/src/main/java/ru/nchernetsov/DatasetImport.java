package ru.nchernetsov;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class DatasetImport {

    public static String[] parseFile(String strpath) {
        Path path = Paths.get(strpath);
        try (Stream<String> lines = Files.lines(path)) {
            ArrayList<String> result = new ArrayList<String>();
            lines.forEach(s -> {
                StringTokenizer token = new StringTokenizer(s, " .,<>@-=():_';\"");
                while (token.hasMoreTokens())
                    result.add(token.nextToken());
            });
            return result.toArray(new String[0]);
        } catch (IOException ignored) {
        }
        return null;
    }


    public static void main(String[] argv) {
        System.out.println("import");
        String[] words = parseFile("C:\\!Work\\Otus\\10 Êðàñíî-÷åðíûå äåðåâüÿ\\dz\\wikitext-2-v1\\wikitext-2\\wiki.train.tokens");
        System.out.println(words.length);
        //for(String word : words)
        //System.out.println(word);
    }

}
