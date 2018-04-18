package br.com.timer.rest.domain.clock.assembler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.timer.domain.clock.Clock;
import br.com.timer.domain.user.User;
import br.com.timer.intervals.Interval;
import br.com.timer.intervals.IntervalTransformer;
import br.com.timer.intervals.IntervalWarningMessage;
import br.com.timer.intervals.overtime.OvertimeFactory;
import br.com.timer.rest.domain.clock.resources.ClockResource;
import br.com.timer.rest.domain.clock.resources.DayResource;
import br.com.timer.rest.services.AbstractAssembler;
import br.com.timer.util.Util;

@Component
public class ClockResourcesAssembler extends AbstractAssembler<Clock, ClockResource> {
   
	public List<ClockResource> convert(List<Clock> projects) {
    	return projects.stream()
			.map(clock -> mapper.map(clock, ClockResource.class))
			.collect(Collectors.toList());
    }
    
    public ClockResource convert(Clock project) {
        return mapper.map(project, ClockResource.class);
    }

	public ClockResource createResource(User user, List<Clock> clocks) {
		return ClockResource.builder()
				.name(user.getName())
				.pis(user.getPis())
				.days(createDays(clocks))
				.build();
	}

	private List<DayResource> createDays(List<Clock> clocks) {
		Map<Integer, List<LocalDateTime>> map = 
				clocks.stream().collect(
					Collectors.groupingBy(Clock::getDay, 
							Collectors.mapping(Clock::getTimer, Collectors.toList()))
		);
		
		return map.entrySet().stream()
				.map(entry -> createDayResource(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	/**
	 * Create resoruce by day and clocks of day
	 * @param day day
	 * @param clocks clocks of day
	 * @return {@link DayResource}
	 */
	private DayResource createDayResource(int day, List<LocalDateTime> clocks) {
		DayResource resource = new DayResource();
		resource.setDay(day);
		
		List<Interval> intervals = IntervalTransformer.execute(clocks);
		
		IntervalWarningMessage warningMessage = IntervalWarningMessage.getInstance();
		String warning = warningMessage.verify(intervals);
		if (warning != null) {
			resource.setWarnings(Arrays.asList(warning)); // can have more than one intervals with warnings, but the time dont left me improve this :/
		}
		
		List<String> hours = clocks.stream()
			.sorted(Comparator.naturalOrder())
			.map(clock -> Util.formatHours(clock))
			.collect(Collectors.toList());
		resource.setHours(hours);
		
		resource.setTotal(OvertimeFactory.getInstance().totalOvertime(intervals));
		
		return resource;
	}
}
