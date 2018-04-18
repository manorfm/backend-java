package br.com.timer.domain.clock.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.timer.domain.clock.Clock;
import br.com.timer.domain.clock.QClock;

public class ClockRepositoryImpl implements ClockRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Clock> findByUserAndPeriod(long pis, int month, int year) {
		QClock clock = QClock.clock;
		
		return new JPAQuery<Clock>(entityManager)
			.from(clock)
			.where(
					clock.timer.year().eq(year)
				.and(clock.timer.month().eq(month))
				.and(clock.user.pis.eq(pis))
			)
			.orderBy(clock.timer.asc())
			.fetch();
	}

}
