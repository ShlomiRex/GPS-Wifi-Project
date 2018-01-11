package Utils.Filters;

public class Or_Filter implements Filter{
    private Filter filter1, filter2;
    public void And_Filter(Filter filter1, Filter filter2) {
        this.filter1 = filter1;
        this.filter2 = filter2;
    }


    @Override
    public boolean test() {
        return filter1.test() || filter2.test();
    }
}
