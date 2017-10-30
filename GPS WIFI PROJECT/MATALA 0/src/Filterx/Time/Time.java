package Filterx.Time;

import java.time.LocalTime;

public class Time implements Comparable<Time>{

	//24-60-60
	//Hours-Minus-Seconds
	private LocalTime time;
	/**
	 * 
	 * @param time_string Time string must not contain spaces, and only numbers, with : seperating hours minutes and seconds.
	 * Also must enter valid time. No minuses, no 25 hours...
	 * Can throw run time exception if not follow rules.
	 */
	public Time(String time_string) {
		time = LocalTime.parse(time_string);
	}
	
	public LocalTime getTime() {
		return time;
	}
	
	@Override
	public int compareTo(Time t) {
		return this.time.compareTo(t.getTime());
	}
}
