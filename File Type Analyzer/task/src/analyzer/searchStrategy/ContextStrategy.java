package analyzer.searchStrategy;


/**
 * Context class to set the concrete strategy to be used in the Strategy Search pattern
 */
class SearchContext {
    private SearchStrategy SearchStrategy;

    private SearchContext(SearchStrategy SearchStrategy) {
        this.SearchStrategy = SearchStrategy;
    }

    static SearchContext factory(SearchStrategy SearchStrategy) {
        return new SearchContext(SearchStrategy);
    }

    static SearchContext nullFactory() {
        return new SearchContext(null);
    }

    void setSubstringMethod(SearchStrategy SearchStrategy) {
        this.SearchStrategy = SearchStrategy;
    }

    boolean search() {
        return this.SearchStrategy.search();
    }
}
