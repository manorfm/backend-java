package br.com.timer.intervals;

import java.time.Duration;
import java.util.List;

import lombok.Getter;

/**
 * Calc the time of rest and total time worked. 
 * 
 * @author Manoel Medeiros
 */
@Getter
public class IntervalTimeCalculate {

	private long totalTimeWorked = 0;
	private long totalTimeOfInterval = 0;
	
	/**
	 * execute calc of the time of rest and total time worked.
	 * 
	 * @param list of intervals {@link Interval}
	 */
	public void execute(List<Interval> intervals) {
		Interval last = null;
		for (Interval interval : intervals) {
			totalTimeWorked += toMinutes(interval.diff());
			
			if (last != null) {
				Duration duration = Duration.between(last.getEnd(), interval.getStart());
				totalTimeOfInterval += toMinutes(duration.toMillis());
			}
			
			last = interval;
		}
	}
	
	private long toMinutes(long value) {
		return value / 1000 / 60;
	}
}
