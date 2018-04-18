package br.com.timer.intervals.overtime;

import java.time.LocalDateTime;
import java.util.Arrays;

public enum EOvertimeMultiplier {

	Monday(1, 1.2),
	Tuesday(2, 1.2),
	Wednesday(3, 1.2),
	Thursday(4, 1.2),
	Friday(5, 1.2),
	Saturday(6, 1.5),
	Sunday(7, 2);
	
	private int day;
	private double multiplier;
	
	private EOvertimeMultiplier(int day, double multiplier) {
		this.day = day;
		this.multiplier = multiplier;
	}
	
	public int getDay() {
		return day;
	}
	
	public double getMultiplier() {
		return multiplier;
	}
	
	public static EOvertimeMultiplier of(LocalDateTime date) {
		return Arrays.stream(values()).parallel()
			.filter(overtime -> overtime.day == date.getDayOfWeek().getValue())
			.findFirst()
			.get();
	}
	
	public boolean isSaturday() {
		return this.equals(Saturday);
	}

	public boolean isSunday() {
		return this.equals(Sunday);
	}

	public boolean isFriday() {
		return this.equals(Friday);
	}

	public boolean isMonday() {
		return this.equals(Friday);
	}
	
	public boolean isNotWeekend() {
		return !(isSaturday() || isSunday());
	}
}
