package analyzer.searchStrategy;

import java.util.Arrays;

/**
 *Concrete implementation of the search strategy using KMP algo
 */
class ConcreteSearchKMP extends SearchStrategy {
    int[] prefixFunction;

    protected ConcreteSearchKMP(String pattern, String text) {
        super(pattern, text);
        prefixFunction = new int[pattern.length()];

    }


    private void setPrefixFunction() {
        prefixFunction[0] = 0;
        for (int i = 1; i < pattern.length(); i++) {
            int j = prefixFunction[i - 1];
            if (pattern.charAt(j) == pattern.charAt(i)) {
                prefixFunction[i] = prefixFunction[i - 1] + 1;
            }
            while (j != 0) {
                j = prefixFunction[j - 1];
                if (pattern.charAt(j) == pattern.charAt(i)) {
                    prefixFunction[i] = prefixFunction[i - 1] + 1;
                    break;
                }
            }
        }
    }

    @Override
    protected boolean search() {
        int j = 0;
        int i = 0;
        int M = pattern.length();
        setPrefixFunction();
        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
                if (j == M) {
                    return true;
                }
            } else {
                if (j != 0) {
                    j = this.prefixFunction[j - 1];
                } else {
                    i = i + 1;
                }
            }
        }
        return false;
    }
}
