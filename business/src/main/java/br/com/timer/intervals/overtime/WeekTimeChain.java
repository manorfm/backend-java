package br.com.timer.intervals.overtime;

import java.util.List;

import br.com.timer.intervals.Interval;

/**
 * Calcule overtime in week's interval
 * 
 * @author Manoel Medeiros
 *
 */
public class WeekTimeChain implements IOvertimeChain {

	private IOvertimeChain next;
	
	@Override
	public IOvertimeChain next(IOvertimeChain next) {
		this.next = next;
		return this;
	}

	@Override
	public long process(long overtime, List<Interval> intervals) {
		
		overtime =+ intervals.parallelStream()
				.filter(interval -> EOvertimeMultiplier.of(interval.getEnd()).isNotWeekend())
				.mapToLong(interval -> getJustTimeOfWeek(interval))
				.reduce(0, (x, y) -> x + y); 
		
		if (next != null) {
			return next.process(overtime, intervals);
		}
		return overtime;
	}

	private long getJustTimeOfWeek(Interval interval) {
		if (interval.getEnd() == null) {
			return 0;
		}
		
		if (interval.getStart().getHour() < 6 && interval.getEnd().getHour() > 6) {
			Interval intervalTime = new Interval();
			
			intervalTime.add(interval.getStart().withHour(6));
			intervalTime.add(interval.getEnd());
			
			return intervalTime.diff();
		} else if (interval.getStart().getHour() < 22 && interval.getEnd().getHour() >= 22) {
			Interval intervalTime = new Interval();
			
			intervalTime.add(interval.getStart());
			intervalTime.add(interval.getEnd().withHour(22));
			
			return intervalTime.diff();
		}
		
		return interval.diff();
	}

}
