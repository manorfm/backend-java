package br.com.timer.intervals.overtime;

import java.util.List;

import br.com.timer.intervals.Interval;
import br.com.timer.util.Util;

/**
 * Factory to sum total of hours worked with overtime calculated
 * 
 * @author Manoel Medeiros
 *
 */
public class OvertimeFactory {

	private static OvertimeFactory instance;
	
	private OvertimeFactory() { }
	
	public static OvertimeFactory getInstance() {
		synchronized (OvertimeFactory.class) {
			if (instance == null) {
				instance = new OvertimeFactory();
			}
			return instance;
		}
	}
	
	public String totalOvertime(List<Interval> intervals) {
		WeekTimeChain chain = new WeekTimeChain();
		chain.next(new WeekOvertimeChain()
				.next(new SaturdayOvertimeChain()
						.next(new SundayOvertimeChain())));
		
		long totalTime = chain.process(0, intervals);
		
		return Util.formatHours(totalTime);
	}
}
