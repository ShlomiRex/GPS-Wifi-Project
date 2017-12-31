package CSV.Enums;

public enum EWigleCSVHeader_WigleHeader {
    Wigle(0), appRelease(1) ,model(2), release(3) , device(4), display(5), board(6), brand(7);
    public int column;

    EWigleCSVHeader_WigleHeader(int column) {
        this.column = column;
    }
}
