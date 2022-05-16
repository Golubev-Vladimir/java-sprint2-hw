package service;

import java.util.Objects;

import static service.FileReader.readFileContentsOrNull;

public class Handler {
    static final String TASK_LINE_DELIMITER = "\n";
    static final String SPLITTER = ",";

    private Handler() {
    }

    public static String[] fileParseForLoadTasks(String value) {
        try {
            return Objects.requireNonNull(readFileContentsOrNull(value)).split(TASK_LINE_DELIMITER);
        } catch (Exception e) {
            return new String[0];
        }
    }

    public static String[] split(String value) {
        return value.split(SPLITTER);
    }
}