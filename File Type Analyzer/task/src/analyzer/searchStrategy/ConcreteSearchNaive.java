package analyzer.searchStrategy;


import java.util.Arrays;

/**
 * Concrete implementation of the Search Strategy using naive algo
 */
class ConcreteSearchNaive extends SearchStrategy {
    private int[] prefixFunction;
    protected ConcreteSearchNaive(String pattern, String text) {
        super(pattern, text);
    }


    @Override
    protected boolean search() {
        return text.contains(pattern);
    }
}
