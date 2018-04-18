package br.com.timer.intervals.overtime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.timer.intervals.Interval;
import br.com.timer.intervals.IntervalTransformer;
import br.com.timer.util.Util;

public class TestOvertimeChain {

	@Test
	public void testOvertimeSaturdayChain() {
		List<LocalDateTime> saturdayClocks = getClocks(21); // saturday
		List<Interval> saturdayIntervals = IntervalTransformer.execute(saturdayClocks);
		
		assertThat(saturdayIntervals, hasSize(1));
		
		SaturdayOvertimeChain chain = new SaturdayOvertimeChain();
		
		assertEquals(10800000, chain.process(0, saturdayIntervals));
		assertEquals("03:00:00", Util.formatHours(chain.process(0, saturdayIntervals)));
	}

	@Test
	public void testNoOvertimeSaturdayChain() {
		List<LocalDateTime> saturdayClocks = getClocks(22); // sunday
		List<Interval> saturdayIntervals = IntervalTransformer.execute(saturdayClocks);
		
		assertThat(saturdayIntervals, hasSize(1));
		
		SaturdayOvertimeChain chain = new SaturdayOvertimeChain();
		
		assertEquals(0, chain.process(0, saturdayIntervals));
		assertEquals("00:00:00", Util.formatHours(chain.process(0, saturdayIntervals)));
	}
	
	@Test
	public void testOvertimeSundayChain() {
		List<LocalDateTime> clocks = getClocks(22); // sunday
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(1));
		
		SundayOvertimeChain chain = new SundayOvertimeChain();
		
		assertEquals(14400000, chain.process(0, intervals));
		assertEquals("04:00:00", Util.formatHours(chain.process(0, intervals)));
	}

	@Test
	public void testNoOvertimeSundayChain() {
		List<LocalDateTime> clocks = getClocks(21); // saturday
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(1));
		
		SundayOvertimeChain chain = new SundayOvertimeChain();
		
		assertEquals(0, chain.process(0, intervals));
		assertEquals("00:00:00", Util.formatHours(chain.process(0, intervals)));
	}

	@Test
	public void testFullOvertimeWeekChain() {
		List<LocalDateTime> clocks = getClocks(17, 00); // tuesday
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(1));
		
		WeekOvertimeChain chain = new WeekOvertimeChain();
		
		assertEquals(8640000, chain.process(0, intervals));
		assertEquals("02:24:00", Util.formatHours(chain.process(0, intervals)));
	}

	@Test
	public void testPartOvertimeWeekChain() {
		List<LocalDateTime> clocks = getClocks(16, 23); // monday
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(1));
		
		WeekOvertimeChain chain = new WeekOvertimeChain();
		
		assertEquals(4320000, chain.process(0, intervals));
		assertEquals("01:12:00", Util.formatHours(chain.process(0, intervals)));
	}

	@Test
	public void testNoOvertimeWeekChain() {
		List<LocalDateTime> clocks = getClocks(16, 22); // monday
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(1));
		
		WeekOvertimeChain chain = new WeekOvertimeChain();
		
		assertEquals(0, chain.process(0, intervals));
		assertEquals("00:00:00", Util.formatHours(chain.process(0, intervals)));
	}
	
	@Test
	public void testTimeWeekChain() {
		List<LocalDateTime> clocks = getClocks(16, 21); // monday
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(1));
		
		WeekTimeChain chain = new WeekTimeChain();
		
		assertEquals(7200000, chain.process(0, intervals));
		assertEquals("02:00:00", Util.formatHours(chain.process(0, intervals)));
	}

	@Test
	public void testChainNoOverTime() {
		List<LocalDateTime> clocks = getClocks(16, 21); // monday
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(1));
		
		WeekTimeChain chain = new WeekTimeChain();
		chain.next(new WeekOvertimeChain()
			.next(new SaturdayOvertimeChain()
			.next(new SundayOvertimeChain())));
		
		assertEquals(7200000, chain.process(0, intervals));
		assertEquals("02:00:00", Util.formatHours(chain.process(0, intervals)));
	}

	@Test
	public void testChainWithOverTime() {
		List<LocalDateTime> clocks = getClocks(16, 23); // monday
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(1));
		
		WeekTimeChain chain = new WeekTimeChain();
		chain.next(new WeekOvertimeChain()
				.next(new SaturdayOvertimeChain()
						.next(new SundayOvertimeChain())));
		
		assertEquals(7920000, chain.process(0, intervals));
		assertEquals("02:12:00", Util.formatHours(chain.process(0, intervals)));
	}

	@Test
	public void testChainSaturdayWithOverTime() {
		List<LocalDateTime> clocks = getClocks(21); // saturday
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(1));
		
		WeekTimeChain chain = new WeekTimeChain();
		chain.next(new WeekOvertimeChain()
				.next(new SaturdayOvertimeChain()
						.next(new SundayOvertimeChain())));
		
		assertEquals(10800000, chain.process(0, intervals));
		assertEquals("03:00:00", Util.formatHours(chain.process(0, intervals)));
	}

	@Test
	public void testChainSundayWithOverTime() {
		List<LocalDateTime> clocks = getClocks(22); // sunday
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(1));
		
		WeekTimeChain chain = new WeekTimeChain();
		chain.next(new WeekOvertimeChain()
				.next(new SaturdayOvertimeChain()
						.next(new SundayOvertimeChain())));
		
		assertEquals(14400000, chain.process(0, intervals));
		assertEquals("04:00:00", Util.formatHours(chain.process(0, intervals)));
	}

	@Test
	public void testChainAllDayWorkWithOverTime() {
		List<LocalDateTime> clocks = get9HourWorkClocks();
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(2));
		
		WeekTimeChain chain = new WeekTimeChain();
		chain.next(new WeekOvertimeChain()
				.next(new SaturdayOvertimeChain()
						.next(new SundayOvertimeChain())));
		
		assertEquals(51120000, chain.process(0, intervals));
		assertEquals("14:12:00", Util.formatHours(chain.process(0, intervals)));
	}

	@Test
	public void testChainTotalOverTimeWithFactory() {
		List<LocalDateTime> clocks = get9HourWorkClocks();
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		assertThat(intervals, hasSize(2));

		assertEquals("14:12:00", OvertimeFactory.getInstance().totalOvertime(intervals));
	}
	
	private List<LocalDateTime> get9HourWorkClocks() {
		List<LocalDateTime> clocks = new ArrayList<>();
		clocks.add(LocalDateTime.of(2018, 4, 16, 8, 00));
		clocks.add(LocalDateTime.of(2018, 4, 16, 12, 00));
		clocks.add(LocalDateTime.of(2018, 4, 16, 13, 00));
		clocks.add(LocalDateTime.of(2018, 4, 16, 23, 00));
		
		return clocks;
	}
	
	private List<LocalDateTime> getClocks(int day, int hour) {
		LocalDateTime date = LocalDateTime.of(2018, 4, day, hour, 01);
		
		List<LocalDateTime> clocks = new ArrayList<>();
		clocks.add(date);
		clocks.add(date.minusHours(2));
		
		return clocks;
	}
	
	private List<LocalDateTime> getClocks(int day) {
		return getClocks(day, 12);
	}
}
