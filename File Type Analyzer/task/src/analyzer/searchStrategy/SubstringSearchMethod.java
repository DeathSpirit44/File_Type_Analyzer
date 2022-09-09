package analyzer.searchStrategy;

/**
 * Client using the Strategy method according to the parameter given
 */
public class SubstringSearchMethod {
    /**
     * Search the given file
     * @param argument algo
     * @param pattern pattern researched
     * @param text text to be processed
     * @return true if the pattern is in the text
     */
    public static boolean searchSubstring(String argument, String pattern, String text) {
        SearchContext searchContext = SearchContext.nullFactory();
        switch (argument) {
            case "--naive" -> searchContext.setSubstringMethod(new ConcreteSearchNaive(pattern, text));
            case "--KMP" -> searchContext.setSubstringMethod(new ConcreteSearchKMP(pattern, text));
        }
        return searchContext.search();

    }

}
