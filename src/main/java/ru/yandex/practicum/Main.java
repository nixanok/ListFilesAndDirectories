package ru.yandex.practicum;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        File rootPath = new File("src/main/resources/root");
        ScannerFiles.begin(rootPath);
    }
}