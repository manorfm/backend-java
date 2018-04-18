package br.com.timer.intervals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.lessThan;

import java.time.LocalDateTime;

import org.junit.Test;

public class TestInterval {

	@Test
	public void testInterval() {
		Interval interval = new Interval();
		LocalDateTime date = LocalDateTime.now();
		
		interval.add(date.minusHours(1));
		assertEquals(interval.is(), Boolean.FALSE);
		
		interval.add(date);
		assertEquals(interval.is(), Boolean.TRUE);
		
		assertThat(interval.getStart(), notNullValue());		
		assertThat(interval.getEnd(), notNullValue());
	}
	
	@Test
	public void testIntervalOrder() {
		Interval interval = new Interval();
		LocalDateTime date = LocalDateTime.now();
		
		interval.add(date.minusHours(1));
		interval.add(date);
		
		assertThat(interval.getStart(), lessThan(interval.getEnd()));
	}

	@Test
	public void testIntervalInverseOrder() {
		Interval interval = new Interval();
		LocalDateTime date = LocalDateTime.now();
		
		interval.add(date);
		interval.add(date.minusHours(1));
		
		assertThat(interval.getStart(), lessThan(interval.getEnd()));
	}
}
