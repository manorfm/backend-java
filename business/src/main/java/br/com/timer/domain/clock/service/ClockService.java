package br.com.timer.domain.clock.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.timer.domain.clock.Clock;
import br.com.timer.domain.clock.repository.ClockRepository;
import br.com.timer.domain.user.User;
import br.com.timer.exception.TimeLimitExceededException;
import br.com.timer.intervals.Interval;

@Service
public class ClockService {

	@Autowired
	private ClockRepository clockRepository;
	
	@Transactional
	public void save(Clock clock) {
        clockRepository.save(clock);
	}

	@Transactional
    public void remove(Clock clock) {
	    clockRepository.delete(clock);
    }

	@Transactional
    public void remove(Long id) {
	    Clock clock = get(id);
	    clockRepository.delete(clock);
    }
	
	@Transactional
	public Clock get(Long id) {
	    return clockRepository.findOne(id);
	}

	@Transactional
	public void clockIn(User user, LocalDateTime dateTime) {
		Clock clock = new Clock(user, dateTime);
		validations(user, dateTime);
		clockRepository.save(clock);
	}
	
	private void validations(User user, LocalDateTime dateTime) {
		Clock clock = clockRepository.findTopTimerClockByUserPis(user.getPis());
		if (clock != null) {
			Interval interval = new Interval(dateTime, clock.getTimer());
			if ((interval.diff() / 1000) <= 60) {
				throw new TimeLimitExceededException("new check point is not allowed in less than 1 minute");
			}
		}
	}

	@Transactional
	public List<Clock> findByMonth(int month, int year) {
		return clockRepository.findByMonth(month, year);
	}

	@Transactional
	public List<Clock> findByUserAndPeriod(long pis, int month, int year) {
		return clockRepository.findByUserAndPeriod(pis, month, year);
	}
}