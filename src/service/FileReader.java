package service;

import model.ManagerLoadException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static service.Printer.println;

public class FileReader {

    private FileReader() {
    }

    public static String readFileContentsOrNull(String path) {
        try {
            if (path == null) {
                throw new ManagerLoadException("В файле отсутсвуют данные для загрузки");
            }
            return Files.readString(Path.of(path));
        } catch (IOException | ManagerLoadException e) {
            println("Проблемы с файлом");
            println(e.getMessage());
            return "";
        }
    }
}