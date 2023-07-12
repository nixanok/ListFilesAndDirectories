package ru.yandex.practicum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ScannerFiles {

    public static final String PATH_TO_RESULT = "src/main/resources/result.txt";

    public static void begin(File root) {
        File fileResult = new File(PATH_TO_RESULT);
        try (Writer w = new FileWriter(fileResult)) {
            w.write(scan(root, new StringBuilder()).toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static StringBuilder scan(File rootDirectory, final StringBuilder counter) throws IOException {
        StringBuilder result = new StringBuilder();
        List<File> directories = new ArrayList<>();
        File[] files = rootDirectory.listFiles((dir, name) -> !name.equals(".DS_Store"));
        assert files != null;
        Arrays.sort(files, Comparator.comparing(File::getName));

        int localCounter = 1;
        for (File file : files) {
            if (file.isDirectory()) {
                directories.add(file);
                result
                        .append(counter).append(localCounter)
                        .append("папка : ").append(file.getName())
                        .append("\n");
            }
            else if (file.isFile()){
                result
                        .append(counter).append(localCounter)
                        .append("файл : ").append(file.getName())
                        .append("\n");
            }
            localCounter++;
        }

        result.append("\n");

        int directoryCounter = 1;
        directories.sort(Comparator.comparing(File::getName));
        for (File directory : directories) {
            result.append("В папке ").append(directory.getName()).append("\n");

            result.append(scan(directory, new StringBuilder(counter).append(directoryCounter).append(".")));
            directoryCounter++;
        }


        return result;
    }
}
