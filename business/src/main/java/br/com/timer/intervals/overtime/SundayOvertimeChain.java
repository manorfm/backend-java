package br.com.timer.intervals.overtime;

import java.util.List;

import br.com.timer.intervals.Interval;

/**
 * Calcule overtime in sunday's interval
 * 
 * @author Manoel Medeiros
 *
 */
public class SundayOvertimeChain implements IOvertimeChain {

	private IOvertimeChain next;
	
	@Override
	public IOvertimeChain next(IOvertimeChain next) {
		this.next = next;
		return this;
	}

	@Override
	public long process(long overtime, List<Interval> intervals) {
		
		overtime += intervals.parallelStream()
				.filter(interval -> interval.getEnd() != null && EOvertimeMultiplier.of(interval.getEnd()).isSunday())
				.mapToLong(interval -> getOvertimeJustOfWeekend(interval))
				.reduce(0, (x, y) -> x + y) * EOvertimeMultiplier.Sunday.getMultiplier();
		
		if (next != null) {
			return next.process(overtime, intervals);
		}
		
		return overtime;
	}
	
	private long getOvertimeJustOfWeekend(Interval interval) {
		if (EOvertimeMultiplier.of(interval.getStart()).isSaturday()) {
			Interval intervalOvertime = new Interval();
			
			intervalOvertime.add(interval.getEnd().withHour(0).withMinute(0));
			intervalOvertime.add(interval.getEnd());

			return intervalOvertime.diff();
		} else if (EOvertimeMultiplier.of(interval.getEnd()).isMonday()) {
			Interval intervalOvertime = new Interval();
			
			intervalOvertime.add(interval.getStart());
			intervalOvertime.add(interval.getEnd().withHour(0).withMinute(0));

			return intervalOvertime.diff();
		}
		return interval.diff();
	}

}
