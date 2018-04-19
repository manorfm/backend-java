package br.com.timer.intervals;

import java.time.Duration;
import java.time.LocalDateTime;

import br.com.timer.util.Util;
import lombok.Getter;

@Getter
/**
 * Represent one interval of dates, with start and end dates.
 * 
 * @author Manoel Medeiros
s */
public class Interval {
	
	public Interval() { }
	
	public Interval(LocalDateTime o1, LocalDateTime o2) {
		this.add(o1);
		this.add(o2);
	}

	private LocalDateTime start;
	private LocalDateTime end;
	
	/**
	 * Add date to interval
	 * if interval is empty, the first date will be placed as start.
	 * if interval is only with start date, the next date will be placed as end;
	 * 
	 * @param date {@link LocalDateTime}
	 */
	public void add(LocalDateTime date) {
		if (start == null) {
			start = date;
		} else if (start.isBefore(date)) {
			end = date;
		} else {
			end = start;
			start = date;
		}
	}
	
	/**
	 * It's one interval, with start and end dates.
	 * 
	 * @return true if is interval, false otherwise.
	 */
	public boolean is() {
		return start != null && end != null;
	}

	/**
	 * verify if it's part of interval, with just start date.
	 * 
	 * @return true if it' part of interval, false otherwise.
	 */
	public boolean part() {
		return start != null;
	}
	
	/**
	 * Value of time of interval in milleseconds
	 * 
	 * @return time of interval in milleseconds
	 */
	public long diff() {
		if (is()) {
			Duration duration = Duration.between(start, end);
			return duration.toMillis();
		}
		return 0;
	}
	
	/**
	 * Return the time in hour of interval with HH:mm format.
	 * 
	 * @return return time in hour
	 */
	public String getIntervalTimeFormatted() {
		long diff = diff();
		return Util.formatHours(diff);
	}
}
