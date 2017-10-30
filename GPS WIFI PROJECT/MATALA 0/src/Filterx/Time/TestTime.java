package Filterx.Time;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestTime {

	@Test
	public void test() {
		Time t1 = new Time("16:52:12");
		Time t2 = new Time("16:55:55");
		Time t3 = new Time("16:55:55");
		
		assertEquals(-1, t1.compareTo(t2));
		assertEquals(1, t2.compareTo(t1));
		assertEquals(0, t3.compareTo(t2));
		
		
	}

}
