package Database.Filters.OperationFilters;

import Database.Filters.Base.AbstractFilter;

public class Not_Filter extends AbstractFilter {
    private AbstractFilter filter;
    public void And_Filter(AbstractFilter filter) {
        this.filter = filter;
    }

    @Override
    public boolean test(Object o) {
        return ! filter.test(o);
    }
}
