package analyzer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Static class for extracting various data from files
 */
public class ExtractFilesUtilsNEW {
    /**
     * Extract the patterns and their priority from a .db file
     * @param filePath filepath of the file where the patterns are stored
     * @return return a Map of the pattern (key) and a PatternData (value)
     */
    static Map<String, PatternData> extractPatterns(String filePath) {
        File file = new File(filePath);
        Map<String, PatternData> map = new HashMap<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");
                String[] processedValues = new String[values.length];
                processedValues[0] = values[0].trim();
                processedValues[1] = values[1].trim();
                processedValues[2] = values[2].trim();
                StringBuilder pattern = new StringBuilder(processedValues[1]);
                StringBuilder fileType = new StringBuilder(processedValues[2]);
                pattern.deleteCharAt(0);
                pattern.deleteCharAt(pattern.length() - 1);
                fileType.deleteCharAt(0);
                fileType.deleteCharAt(fileType.length() - 1);
                PatternData patternData = new PatternData(fileType.toString(), Integer.parseInt(processedValues[0]));
                map.put(pattern.toString(), patternData);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Convert the data of a binary file to string
     * @param filePath relative path of the binary file
     * @return the data of the file on a String
     */
    static String extractTextFromBinary(String filePath) {
        File file = new File(filePath);
        byte[] bytes;
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)
        ) {
            bytes = bufferedInputStream.readAllBytes();
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

/**
 * Record for storing values associated to the key pattern in the patternMap
 * @param fileType
 * @param priority
 */
record PatternData(String fileType, Integer priority) {
}
