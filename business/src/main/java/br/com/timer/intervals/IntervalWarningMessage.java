package br.com.timer.intervals;

import java.util.List;

public class IntervalWarningMessage {

	private static IntervalWarningMessage instance;
	
	private IntervalWarningMessage() { }
	
	public static IntervalWarningMessage getInstance() {
		synchronized (IntervalWarningMessage.class) {
			if (instance == null) {
				instance = new IntervalWarningMessage();
			}
			
			return instance;
		}
	}
	
	public String verify(List<Interval> intervals) {
		IntervalTimeCalculate timer = new IntervalTimeCalculate();
		
		timer.execute(intervals);
		
		if (timer.getTotalTimeWorked() > toMinutes(4) && timer.getTotalTimeOfInterval() == 0) {
			return "You need interval rest in case you worked more than 4 hours";
		} else if (timer.getTotalTimeWorked() > toMinutes(4) && timer.getTotalTimeWorked() < toMinutes(6)
				&& timer.getTotalTimeOfInterval() < 15) {
			return "The minimum time of interval between 4h and 6h of work is 15 minutes.";
		} else if (timer.getTotalTimeWorked() > toMinutes(6) && timer.getTotalTimeOfInterval() < toMinutes(1)) {
			return "The minimum time of interval above 6h of work is 1 hours.";
		}
		
		return null;
	}
	
	/**
	 * Convert hours to minutes
	 * 
	 * @param value in hours
	 * @return value in minutes
	 */
	private long toMinutes(int value) {
		return value * 60;
	}
}
