package br.com.timer.intervals.overtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

public class TestOvertimeMultiplier {

	@Test
	public void testSearchDayOfDateTime() {
		assertEquals(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 22, 12, 01)), EOvertimeMultiplier.Sunday);
		assertEquals(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 21, 12, 01)), EOvertimeMultiplier.Saturday);
		assertEquals(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 20, 12, 01)), EOvertimeMultiplier.Friday);
		assertEquals(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 19, 12, 01)), EOvertimeMultiplier.Thursday);
		assertEquals(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 18, 12, 01)), EOvertimeMultiplier.Wednesday);
		assertEquals(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 17, 12, 01)), EOvertimeMultiplier.Tuesday);
		assertEquals(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 16, 12, 01)), EOvertimeMultiplier.Monday);
	}

	@Test
	public void testIsWeekend() {
		assertTrue(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 22, 12, 01)).isSunday());
		assertTrue(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 21, 12, 01)).isSaturday());
		assertTrue(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 20, 12, 01)).isNotWeekend());
		assertTrue(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 19, 12, 01)).isNotWeekend());
		assertTrue(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 18, 12, 01)).isNotWeekend());
		assertTrue(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 17, 12, 01)).isNotWeekend());
		assertTrue(EOvertimeMultiplier.of(LocalDateTime.of(2018, 4, 16, 12, 01)).isNotWeekend());
	}
}
