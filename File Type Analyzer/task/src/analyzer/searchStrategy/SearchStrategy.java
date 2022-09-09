package analyzer.searchStrategy;


/**
 * Abstract class for the search Strategy
 */
abstract class SearchStrategy {
    final protected String pattern;
    final protected String text;

    protected SearchStrategy(String pattern, String text) {
        this.pattern = pattern;
        this.text = text;
    }

    protected abstract boolean search();
}
