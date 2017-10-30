package CSV;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;


public class Fields {
	
	public static final int BSSID = 0, LAT = 1, LON = 2, SSID = 3, Crypt = 4, Beacon_Interval = 5,
			Connection_Mode = 6, Channel = 7, RXL = 8, Date = 9, Time = 10;
	
	
	private Date date;
	private LocalTime time;
	private String bssid;
	
	public Fields(Record record) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy, mm, dd");
		date = format.parse(record.get_Field(this.Date));
		time = LocalTime.parse( record.get_Field(this.Time) ) ;
		bssid = record.get_Field(this.BSSID);
	}

}
