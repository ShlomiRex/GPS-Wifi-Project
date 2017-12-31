package CSV.Data;

public class WifiSpectrum {
    public double rssi, channel;

    public WifiSpectrum(double rssi, double channel) {
        this.rssi = rssi;
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "RSSI: " + rssi + " CHANNEL: " + channel;
    }
}
