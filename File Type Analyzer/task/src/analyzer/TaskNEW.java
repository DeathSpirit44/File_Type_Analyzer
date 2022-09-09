package analyzer;

import analyzer.searchStrategy.SubstringSearchMethod;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @param pattern pattern of to be found on the file
 * @param found true if patter is in the file
 * @param map map storing the date of the patterns
 */
record TaskResult(String pattern, boolean found, Map<String, PatternData> map) implements Comparable<TaskResult> {
    /**
     * the comparsison is done only with the priority of the pattern found in the map
     * @param o the object to be compared.
     */
    @Override
    public int compareTo(TaskResult o) {
        return map.get(this.pattern).priority().compareTo(map.get(o.pattern).priority());
    }
}

/**
 * Class representing a task to compute if the pattern is in the file or not
 */
public class TaskNEW implements Callable<TaskResult> {
    private final String parameter;
    private final String pattern;
    private final String filePath;
    private final Map<String, PatternData> map;

    /**
     * Private canonical constructor for factory method
     * @param parameter algo type
     * @param pattern pattern to be found
     * @param filePath file to be processed
     * @param map map of patterns
     */
    private TaskNEW(String parameter, String pattern, String filePath, Map<String, PatternData> map) {
        this.parameter = parameter;
        this.pattern = pattern;
        this.filePath = filePath;
        this.map = map;
    }

    /**
     * Static canonical factory method
     * @param parameter algo type
     * @param pattern pattern to be found
     * @param filePath file to be processed
     * @param map map of patterns
     * @return new TaskNew
     */
    public static TaskNEW createTaskNEW(String parameter, String pattern, String filePath, Map<String, PatternData> map) {
        return new TaskNEW(parameter, pattern, filePath, map);
    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * @return Return a task result for the associated file and pattern
     */
    @Override
    public TaskResult call() {
        return new TaskResult(pattern, SubstringSearchMethod.searchSubstring(parameter, pattern, ExtractFilesUtilsNEW.extractTextFromBinary(filePath)), this.map);
    }
}
