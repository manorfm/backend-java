package br.com.timer.domain.clock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.timer.config.ApplicationConfigTest;
import br.com.timer.domain.clock.service.ClockService;
import br.com.timer.domain.user.User;
import br.com.timer.domain.user.service.UserService;
import br.com.timer.exception.TimeLimitExceededException;
import br.com.timer.security.KeyUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationConfigTest.class})
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
public class TestCheckPoint {

	@Autowired
    private ClockService clockService;

	@Autowired
	private UserService userService;
	
	@Before
	public void startup() {
		User user = new User();
		user.setName("Manoel Medeiros");
		user.setPis(1516548151l);
		user.setPassword(KeyUtil.encodeBase64("manoel:1234"));
		
		userService.save(user);
	}
	
	@Test
	public void testCheckPointStart() {
		User user = userService.get(1516548151l);
		
		clockService.clockIn(user, LocalDateTime.now());
		
		LocalDateTime today = LocalDateTime.now();
		List<Clock> clocks = clockService.findByMonth(today.getMonth().getValue(), today.getYear());
		assertThat(clocks, hasSize(1));
	}

	@Test(expected = TimeLimitExceededException.class)
	public void testCheckPointTwice() {
		User user = userService.get(1516548151l);
		
		clockService.clockIn(user, LocalDateTime.now());
		clockService.clockIn(user, LocalDateTime.now());
	}

	@Test
	public void testCheckPointTwice1WithMuniteInterval() {
		User user = userService.get(1516548151l);
		LocalDateTime today = LocalDateTime.now();
		
		Clock clock = new Clock(user, null);
		clock.setTimer(today.minusMinutes(2));
		clockService.save(clock);
		
		clockService.clockIn(user, LocalDateTime.now());
		
		List<Clock> clocks = clockService.findByMonth(today.getMonth().getValue(), today.getYear());
		assertThat(clocks, hasSize(2));
	}
}
