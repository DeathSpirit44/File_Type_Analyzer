package analyzer;

import analyzer.searchStrategy.SubstringSearchMethod;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Enter 2 arguments");
            System.exit(0);
        }
        String argument ="--KMP";
        String filesPath = args[0];
        String patternsPath = args[1];
        MainTaskNEW mainTaskNEW = MainTaskNEW.MainTaskFactory(argument, filesPath, patternsPath);
        mainTaskNEW.createTasks();
        mainTaskNEW.launchTasks();
        mainTaskNEW.processResults();
    }
}
