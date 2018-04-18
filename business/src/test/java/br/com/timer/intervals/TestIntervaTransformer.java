package br.com.timer.intervals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.notNullValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestIntervaTransformer {

	@Test
	public void testIntervalTransformer() {
		LocalDateTime date = LocalDateTime.now();
		
		List<LocalDateTime> clocks = getClocks(date);
		
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(2));
		
		intervals.stream()//.peek(interval -> System.out.println(interval.getStart() + " - " + interval.getEnd()))
			.forEach(interval -> assertThat(interval.getStart(), lessThan(interval.getEnd())));
	}

	@Test
	public void testIntervalIncompleteTransformer() {
		LocalDateTime date = LocalDateTime.now();
		
		List<LocalDateTime> clocks = new ArrayList<>();
		clocks.add(date);
		clocks.add(date.minusHours(4));
		clocks.add(date.minusHours(8));
		
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(2));
		
		assertThat(intervals.get(0).getStart(), lessThan(intervals.get(0).getEnd()));
		assertThat(intervals.get(1).getStart(), notNullValue());
		assertThat(intervals.get(1).getEnd(), nullValue());
	}
	
	@Test
	public void testIntervalDiffAndTimeFormatted() {
		LocalDateTime date = LocalDateTime.now();
		List<LocalDateTime> clocks = getClocks(date);
		
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(2));
		
		assertEquals(intervals.get(0).getIntervalTimeFormatted(), "02:00:00");
		assertEquals(intervals.get(1).getIntervalTimeFormatted(), "04:00:00");

		assertEquals(intervals.get(0).diff(), 7200000);
		assertEquals(intervals.get(1).diff(), 14400000);
	}

	private List<LocalDateTime> getClocks(LocalDateTime date) {
		List<LocalDateTime> clocks = new ArrayList<>();
		clocks.add(date);
		clocks.add(date.minusHours(6));
		clocks.add(date.minusHours(8));
		clocks.add(date.minusHours(4));
		return clocks;
	}
}
