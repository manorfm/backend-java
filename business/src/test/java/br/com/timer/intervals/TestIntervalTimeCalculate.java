package br.com.timer.intervals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestIntervalTimeCalculate {

	@Test
	public void testIntervalCalc() {
		List<LocalDateTime> clocks = getClocks();
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		intervals.stream()
			.forEach(interval -> assertThat(interval.getStart(), lessThan(interval.getEnd())));
		
		assertThat(intervals, hasSize(2));
		
		IntervalTimeCalculate timeCalculate = new IntervalTimeCalculate();
		timeCalculate.execute(intervals);
		
		assertEquals(timeCalculate.getTotalTimeWorked(), 360);
		assertEquals(timeCalculate.getTotalTimeOfInterval(), 120);
	}
	
	private List<LocalDateTime> getClocks() {
		LocalDateTime date = LocalDateTime.now();
		
		List<LocalDateTime> clocks = new ArrayList<>();
		clocks.add(date);
		clocks.add(date.minusHours(6));
		clocks.add(date.minusHours(8));
		clocks.add(date.minusHours(4));
		return clocks;
	}
}
