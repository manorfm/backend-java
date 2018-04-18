package br.com.timer.rest.domain.clock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.timer.domain.clock.Clock;
import br.com.timer.domain.clock.service.ClockService;
import br.com.timer.domain.user.User;
import br.com.timer.domain.user.service.UserService;
import br.com.timer.rest.domain.clock.assembler.ClockResourcesAssembler;
import br.com.timer.rest.domain.clock.resources.ClockInResource;
import br.com.timer.rest.domain.clock.resources.ClockResource;
import br.com.timer.rest.resources.PageResources;
import br.com.timer.rest.services.AbstractController;

@RestController
@RequestMapping(value = "/clockin", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClockController extends AbstractController {

	@Autowired
	private ClockService clockService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ClockResourcesAssembler clockAssembler;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> clockIn(@RequestBody ClockInResource clockIn) {
		try {
			User user = userService.get(clockIn.getPis());
			clockService.clockIn(user, clockIn.getDateTime());
			return responseOk();
		} catch (Exception e) {
			return exception("User not found", e);
		}
	}
	
	@RequestMapping(value = "/user/{pis}/year/{year}/month/{month}",  method = RequestMethod.GET)
	public ResponseEntity<PageResources<?>> listClockIn(
			@PathVariable long pis, @PathVariable int year, @PathVariable int month) {
		
		try {
			User user = userService.get(pis);
			List<Clock> clocks = clockService.findByUserAndPeriod(pis, month, year);
		
			ClockResource resource = clockAssembler.createResource(user, clocks);
		
			return responseOk(new PageResources<ClockResource>(resource));
		} catch (Exception e) {
			return exception("User not found", e);
		}
	}
}