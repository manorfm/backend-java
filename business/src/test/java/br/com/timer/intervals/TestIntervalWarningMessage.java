package br.com.timer.intervals;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestIntervalWarningMessage {

	@Test
	public void testNoWarninig() {
		List<LocalDateTime> clocks = new ArrayList<>();
		LocalDateTime date = LocalDateTime.now();
		clocks.add(date);
		clocks.add(date.minusHours(6));
		clocks.add(date.minusHours(8));
		clocks.add(date.minusHours(4));
		
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		IntervalWarningMessage warningMessage = IntervalWarningMessage.getInstance();
		
		assertEquals(null, warningMessage.verify(intervals));
	}

	@Test
	public void testNoMinutesIntervalWarninig() {
		List<LocalDateTime> clocks = new ArrayList<>();
		
		LocalDateTime date = LocalDateTime.of(2018, 4, 16, 8, 00);
		clocks.add(date);
		clocks.add(date.minusHours(6));
		
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		IntervalWarningMessage warningMessage = IntervalWarningMessage.getInstance();
		assertEquals("You need interval rest in case you worked more than 4 hours", warningMessage.verify(intervals));
	}
	
	@Test
	public void test15MinutesIntervalWarninig() {
		List<LocalDateTime> clocks = new ArrayList<>();
		
		LocalDateTime date = LocalDateTime.of(2018, 4, 16, 8, 00);
		clocks.add(date);
		clocks.add(date.minusHours(2));
		clocks.add(date.minusHours(2).minusMinutes(12));
		clocks.add(date.minusHours(6));
		
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		IntervalWarningMessage warningMessage = IntervalWarningMessage.getInstance();
		assertEquals("The minimum time of interval between 4h and 6h of work is 15 minutes.", warningMessage.verify(intervals));
	}
	
	@Test
	public void test60MinutesIntervalNoWarninig() {
		List<LocalDateTime> clocks = new ArrayList<>();
		LocalDateTime date = LocalDateTime.now();
		clocks.add(date);
		clocks.add(date.minusHours(4));
		clocks.add(date.minusHours(4).minusMinutes(45));
		clocks.add(date.minusHours(6));
		
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		IntervalWarningMessage warningMessage = IntervalWarningMessage.getInstance();
		assertEquals(null, warningMessage.verify(intervals));
	}

	@Test
	public void test60MinutesIntervalWarninig() {
		List<LocalDateTime> clocks = new ArrayList<>();
		LocalDateTime date = LocalDateTime.now();
		clocks.add(date);
		clocks.add(date.minusHours(4));
		clocks.add(date.minusHours(4).minusMinutes(45));
		clocks.add(date.minusHours(7));
		
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		IntervalWarningMessage warningMessage = IntervalWarningMessage.getInstance();
		assertEquals("The minimum time of interval above 6h of work is 1 hours.", warningMessage.verify(intervals));
	}
	
}
