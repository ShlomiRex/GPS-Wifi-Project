package Algorithems.Data;

import CSV.Data.AP_WifiData;
import Utils.Pair;

public class MatchingInputAndAPWifiData {
    public Pair<String, Double> input;
    public AP_WifiData matchingResult;
    public MatchingInputAndAPWifiData(Pair<String, Double> input, AP_WifiData matchingResult) {
        this.input = input;
        this.matchingResult = matchingResult;
    }
}