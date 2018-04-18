package br.com.timer.intervals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class to transform list of dates on intervals
 * 
 * @author Manoel Medeiros
 */
public class IntervalTransformer {

	/**
	 * Transform the list of dates to {@link Interval}
	 * @param dates list of dates
	 * @return {@link Interval}
	 */
	public static List<Interval> execute(List<LocalDateTime> dates) {
		List<Interval> intervals = new ArrayList<>();
		Collections.sort(dates, Comparator.naturalOrder());
		
		Interval interval = new Interval();
		for (LocalDateTime date : dates) {
			interval.add(date);
			if (interval.is()) {
				intervals.add(interval);
				interval = new Interval();
			}
		}
		if (interval.part()) {
			intervals.add(interval);
		}
		return intervals;
	}
	
}
