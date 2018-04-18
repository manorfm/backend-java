package br.com.timer.domain.clock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.timer.domain.clock.Clock;

@Repository
public interface ClockRepository extends CrudRepository<Clock, Long>, ClockRepositoryCustom {
	
	
	@Query("from Clock c where month(c.timer) = ? and year(c.timer) = ?")
	List<Clock> findByMonth(int month, int year);

	Clock findTopTimerClockByUserPis(Long id);
    
}