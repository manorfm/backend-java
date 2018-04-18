package br.com.timer.intervals.overtime;

import java.util.List;

import br.com.timer.intervals.Interval;

public interface IOvertimeChain {

	IOvertimeChain next(IOvertimeChain next);
	long process(long overtime, List<Interval> intervals);
	
}
