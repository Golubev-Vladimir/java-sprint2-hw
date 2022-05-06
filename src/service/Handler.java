package service;

import java.util.Objects;

import static service.FileReader.readFileContentsOrNull;

public class Handler {

    private Handler() {
    }

    public static String[] fileParseForLoadTasks(String value) {
        try {
            return Objects.requireNonNull(readFileContentsOrNull(value)).split("\n");
        } catch (Exception e) {
            return new String[0];
        }
    }

    public static String[] fileParseForLoadHistory(String value) {
        try {
            String[] fileContains = Objects.requireNonNull(readFileContentsOrNull(value)).split("\n\n");
            return fileContains[1].split(",");
        } catch (Exception e) {
            return new String[0];
        }
    }
}