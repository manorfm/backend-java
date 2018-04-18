package br.com.timer.intervals.overtime;

import java.time.LocalDateTime;
import java.util.List;

import br.com.timer.intervals.Interval;

/**
 * Calcule overtime in week's interval
 * 
 * @author Manoel Medeiros
 *
 */
public class WeekOvertimeChain implements IOvertimeChain {

	private IOvertimeChain next;
	
	@Override
	public IOvertimeChain next(IOvertimeChain next) {
		this.next = next;
		return this;
	}

	@Override
	public long process(long overtime, List<Interval> intervals) {
		
		overtime += intervals.parallelStream()
				.filter(interval -> EOvertimeMultiplier.of(interval.getStart()).isNotWeekend())
				.mapToLong(interval -> getJustOvertimeOfWeek(interval))
				.reduce(0, (x, y) -> x + y) * EOvertimeMultiplier.Monday.getMultiplier(); // for now all non-weekend days has same multiplier
		
		if (next != null) {
			return next.process(overtime, intervals);
		}
		
		return overtime;
	}

	private long getJustOvertimeOfWeek(Interval interval) {
		long overtime = 0;
		if (interval.getEnd() == null) {
			return overtime;
		}
		
		if (interval.getStart().getHour() >= 22 && until(interval)) {
			return interval.diff();
		} else if(interval.getStart().getHour() < 6 && interval.getEnd().getHour() >= 6) { 
			Interval intervalOvertime = new Interval();
			
			intervalOvertime.add(interval.getStart());
			intervalOvertime.add(interval.getEnd().withHour(6));
			
			return intervalOvertime.diff();
		} else if (!EOvertimeMultiplier.of(interval.getEnd()).isFriday() && interval.getEnd().getHour() >= 22) {
			Interval intervalOvertime = new Interval();
			
			intervalOvertime.add(interval.getStart().withHour(22));
			intervalOvertime.add(interval.getEnd());
			
			return intervalOvertime.diff();
		}
		
		return overtime;
	}
	
	private boolean until(Interval interval) {
		LocalDateTime end = interval.getEnd();
		if (EOvertimeMultiplier.of(end).isFriday()) {
			return end.getHour() <= 0;
		}
		return end.getHour() <= 6;
	}
}
