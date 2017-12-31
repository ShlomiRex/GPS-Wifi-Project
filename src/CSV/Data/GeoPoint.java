package CSV.Data;

public class GeoPoint {
    public double lat, lon, alt;
    public GeoPoint(double lat, double lon, double alt) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
    }

    public boolean equals(GeoPoint other) {
        if(other.lat != lat || other.lon != lon || other.alt != alt) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String result = "";
        result += lat + ",";
        result += lon + ",";
        result += alt;
        return result;
    }
}
