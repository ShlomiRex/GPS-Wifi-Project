package CSV.Enums;

/**
 * Represents the columns of typical combo file.
 */
public enum EComboDatas {

    FirstSeen(0), ID(1), Lat(2), Lon(3), Alt(4), NumOfDatas(5), StartingDataColumn(6);

    public int column;

    EComboDatas(int column) {
        this.column = column;
    }
}
