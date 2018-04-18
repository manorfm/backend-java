package br.com.timer.domain.clock.repository;

import java.util.List;

import br.com.timer.domain.clock.Clock;

public interface ClockRepositoryCustom {
	
	List<Clock> findByUserAndPeriod(long pis, int month, int year);

}