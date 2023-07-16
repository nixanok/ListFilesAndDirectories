package ru.yandex.practicum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static StringBuilder scan(File rootDirectory, final StringBuilder directoryCounter) throws IOException {
        StringBuilder result = new StringBuilder();

        final List<File> innerDirectories = new ArrayList<>();
        final List<File> innerFiles = new ArrayList<>();

        File[] files = rootDirectory.listFiles((dir, name) -> !name.equals(".DS_Store"));
        assert files != null;

        Arrays.sort(files, (file1, file2) -> file1.getName().toLowerCase().compareTo(file2.getName().toLowerCase()));

        for (File file : files) {
            if (file.isDirectory()) {
                innerDirectories.add(file);
            }
            else if (file.isFile()){
                innerFiles.add(file);
            }
        }

        result.append(directoryCounter);
        if (innerFiles.isEmpty() && innerDirectories.isEmpty()) {
            result.append(" В папке ").append("«").append(rootDirectory.getName()).append("» ничего не обнаружено");
        }
        else if (!innerFiles.isEmpty()){
            result.append(" В папке ").append("«").append(rootDirectory.getName()).append("» обнаружены файлы ");
            for (File file : innerFiles) {
                result
                        .append("«").append(file.getName()).append("»")
                        .append(", ");
            }
            result.delete(result.length() - 2, result.length());
            if (!innerDirectories.isEmpty()) {
                result.append(", а так же папки ");
                for (File directory : innerDirectories) {
                    result
                            .append("«").append(directory.getName()).append("»")
                            .append(", ");
                }
                result.delete(result.length() - 2, result.length());
            }
        } else {
            result.append(" В папке ").append("«").append(rootDirectory.getName()).append("» обнаружены папки ");
            for (File directory : innerDirectories) {
                result
                        .append("«").append(directory.getName()).append("»")
                        .append(", ");
            }
            result.delete(result.length() - 2, result.length());
        }

        result.append(".");
        result.append("\n");

        int localDirectoryCounter = 1;
        for (File directory : innerDirectories) {
            result.append(scan(directory, new StringBuilder(directoryCounter).append(localDirectoryCounter).append(".")));
            localDirectoryCounter++;
        }

        return result;
    }
}