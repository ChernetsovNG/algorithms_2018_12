package ru.nchernetsov.BloomFilter;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * База данных вредоносных доменных имён взята из репозитория:
 * https://github.com/maravento/blackweb
 * Она содержит 2.411.450 доменных имён
 */
public class MaliciousUrlDatabase {

    private static final String DATABASE_FILE_NAME = "blackweb.tar.gz";

    private static final int BUFFER_SIZE = 10 * 1024;  // 10 kB

    private static Set<String> maliciousUrls;

    static {
        // читаем базу данных с вредоносными URL-ами
        maliciousUrls = readDatabaseFile();
    }

    public static boolean urlContainsInDatabase(String url) {
        return maliciousUrls.contains(url);
    }

    public static Iterator<String> getUrlDatabaseIterator() {
        return maliciousUrls.iterator();
    }

    public static int getUrlDatabaseSize() {
        return maliciousUrls.size();
    }

    // читаем из архива файл вредоносных доменных имён и преобразуем его в список строк
    private static Set<String> readDatabaseFile() {
        try {
            ByteArrayOutputStream outputStream = readFileInsideTarArchive();
            Set<String> lines = new HashSet<>();
            String line;
            BufferedReader bufferedReader = new BufferedReader(new StringReader(new String(outputStream.toByteArray())));
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ByteArrayOutputStream readFileInsideTarArchive() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(DATABASE_FILE_NAME)) {
            GzipCompressorInputStream gzipIn = new GzipCompressorInputStream(inputStream);
            try (TarArchiveInputStream tarIn = new TarArchiveInputStream(gzipIn)) {
                TarArchiveEntry entry;
                while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {
                    if (!entry.isDirectory()) {
                        int count;
                        byte[] data = new byte[BUFFER_SIZE];
                        try (BufferedOutputStream dest = new BufferedOutputStream(outputStream, BUFFER_SIZE)) {
                            while ((count = tarIn.read(data, 0, BUFFER_SIZE)) != -1) {
                                dest.write(data, 0, count);
                            }
                        }
                    }
                }
            }
        }
        return outputStream;
    }
}
