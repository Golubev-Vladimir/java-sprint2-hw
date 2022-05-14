package service;

import java.util.Objects;

import static service.FileReader.readFileContentsOrNull;

public class Handler {
    static final String TASK_LINE_DELIMITER = "\n";
    static final String HISTORY_LINE_DELIMITER = "\n\n";
    static final String SPLITTER = ",";
    static final int HISTORY_ARRAY_INDEX = 1;

    private Handler() {
    }

    public static String[] fileParseForLoadTasks(String value) {
        try {
            return Objects.requireNonNull(readFileContentsOrNull(value)).split(TASK_LINE_DELIMITER);
        } catch (Exception e) {
            return new String[0];
        }
    }

    public static String[] fileParseForLoadHistory(String value) {
        try {
            String[] fileContains = Objects.requireNonNull(readFileContentsOrNull(value))
                    .split(HISTORY_LINE_DELIMITER);
            return fileContains[HISTORY_ARRAY_INDEX].split(SPLITTER);
        } catch (Exception e) {
            return new String[0];
        }
    }
}