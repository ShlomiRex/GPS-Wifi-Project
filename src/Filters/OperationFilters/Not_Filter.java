package Filters.OperationFilters;

import Filters.Base.AbstractFilter;

public class Not_Filter extends AbstractFilter {
    private AbstractFilter filter;
    public Not_Filter(AbstractFilter filter) {
        this.filter = filter;
    }

    @Override
    public boolean test(Object o) {
        return ! filter.test(o);
    }
}
