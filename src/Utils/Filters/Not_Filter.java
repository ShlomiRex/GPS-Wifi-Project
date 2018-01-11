package Utils.Filters;

public class Not_Filter implements Filter{
    private Filter filter;
    public void And_Filter(Filter filter) {
        this.filter = filter;
    }


    @Override
    public boolean test() {
        return ! filter.test();
    }
}
