package analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 *Class representing the main task to be run on the main thread
 */
public class MainTaskNEW {
    //Liste of files to be processed
    final private File[] filesList;
    // Map storing data associated to a pattern
    final private Map<String, PatternData> patternsMap;
    // Argument to determine which algo used for parsing text
    final private String parameter;
    // List of List containing all the results obtained of each task [file][result]
    private final List<List<TaskResult>> resultList = new ArrayList<>();
    // List of List containing all the tasks to be run on parrallel thread [file][task]
    private final List<List<TaskNEW>> listOfTasks = new ArrayList<>(); //[file][pattern]

    /**
     * Private constructor for factory method use
     */
    private MainTaskNEW(File[] filesList, Map<String, PatternData> patternsMap, String parameter) {
        this.filesList = filesList;
        this.patternsMap = patternsMap;
        this.parameter = parameter;
    }

    /**
     * Static factory method
     * @param parameter algo used for parsing file
     * @param dirPath path of the directory containing the files to be processed
     * @param patternPath path of the files containing patterns to be compared to
     * @return a new main task associated to the arguments from main
     */
    public static MainTaskNEW MainTaskFactory(String parameter, String dirPath, String patternPath) {
        return new MainTaskNEW(new File(dirPath).listFiles(), ExtractFilesUtilsNEW.extractPatterns(patternPath), parameter);
    }

    /**
     * Extracts the values from the pattern file and list the files to be processed
     * then fill the listOfTasks list with the tasks to be run
     */
    public void createTasks() {
        for (var file :
                filesList) {
            List<TaskNEW> fileTasks = new ArrayList<>();
            patternsMap.forEach((key, value) -> {
                fileTasks.add(TaskNEW.createTaskNEW(parameter, key, file.getPath(), patternsMap));
            });
            listOfTasks.add(fileTasks);
        }
    }

    /**
     * Launch all tasks on parallel threads et store the result (TaskResult) on the fileResult of each task if the task find
     * its pattern in the file.
     */
    public void launchTasks() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<List<Future<TaskResult>>> futureList = new ArrayList<>();
        listOfTasks.forEach((task) -> {
            try {
                futureList.add(executorService.invokeAll(task));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (var list :
                futureList) {
            List<TaskResult> filesResults = new ArrayList<>();
            list.forEach((future) -> {
                try {
                    TaskResult futur = future.get();
                    if (futur.found()) {
                        filesResults.add(futur);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
            resultList.add(filesResults);
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Process the resultList by finding the pattern of max priority and printing its type
     * with the name of the associated file
     */
    public void processResults() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<String> finalResults = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            StringBuilder filename = new StringBuilder(listOfTasks.get(i).get(0).getFilePath());
            filename.delete(0, filename.indexOf("/") + 1);
            if (resultList.get(i).isEmpty()) {
                System.out.println(filename + ": " + "Unknown file type");
            } else {
                System.out.println(filename + ": " + patternsMap.get(Collections.max(resultList.get(i)).pattern()).fileType());
            }


        }
    }
}
