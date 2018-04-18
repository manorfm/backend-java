package br.com.timer.rest.domain.clock;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.timer.domain.clock.Clock;
import br.com.timer.domain.clock.service.ClockService;
import br.com.timer.domain.user.User;
import br.com.timer.domain.user.service.UserService;
import br.com.timer.rest.domain.clock.assembler.ClockResourcesAssembler;
import br.com.timer.rest.domain.clock.controller.ClockController;

public class ClockControllerTest {

	private MockMvc mockMvc;

    @Mock
    private ClockService clockService;

    @Mock
    private UserService userService;

    @Spy
    private ClockResourcesAssembler clockAssembler;

    @InjectMocks
    private ClockController clockController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(clockController)
                .build();
    }
    
	@Test
    public void testClocksOfPeriod() throws Exception {
    	User user = new User();
    	user.setPis(12345l);
    	user.setName("Manoel Medeiros");
    	
    	when(userService.get(12345l)).thenReturn(user);
    	when(clockService.findByUserAndPeriod(12345l, 4, 2018)).thenReturn(getClocks());
    	
    	mockMvc.perform(get("/clockin/user/12345/year/2018/month/4"))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    	.andExpect(jsonPath("$.resource", notNullValue()))
    	.andExpect(jsonPath("$.resource.pis", is(12345)))
    	.andExpect(jsonPath("$.resource.name", is("Manoel Medeiros")))
    	.andExpect(jsonPath("$.resource.days[0].day", is(1)))
    	.andExpect(jsonPath("$.resource.days[0].total", is("16:00:00")))
    	.andExpect(jsonPath("$.resource.days[0].hours[0]", is("08:01:00")))
    	.andExpect(jsonPath("$.resource.days[0].hours[1]", is("12:00:00")))
    	.andExpect(jsonPath("$.resource.days[0].hours[2]", is("13:10:00")))
    	.andExpect(jsonPath("$.resource.days[0].hours[3]", is("17:11:00")))
    	
    	.andExpect(jsonPath("$.resource.days[1].day", is(2)))
    	.andExpect(jsonPath("$.resource.days[1].total", is("08:10:00")))
    	.andExpect(jsonPath("$.resource.days[1].warnings[0]", is("The minimum time of interval above 6h of work is 1 hours.")))
    	.andExpect(jsonPath("$.resource.days[1].hours[0]", is("08:05:00")))
    	.andExpect(jsonPath("$.resource.days[1].hours[1]", is("12:05:00")))
    	.andExpect(jsonPath("$.resource.days[1].hours[2]", is("13:00:00")))
    	.andExpect(jsonPath("$.resource.days[1].hours[3]", is("17:10:00")))

    	.andExpect(jsonPath("$.resource.days[2].day", is(3)))
    	.andExpect(jsonPath("$.resource.days[2].total", is("08:10:00")))
    	.andExpect(jsonPath("$.resource.days[2].hours[0]", is("08:00:00")))
    	.andExpect(jsonPath("$.resource.days[2].hours[1]", is("12:10:00")))
    	.andExpect(jsonPath("$.resource.days[2].hours[2]", is("13:10:00")))
    	.andExpect(jsonPath("$.resource.days[2].hours[3]", is("17:10:00")))

    	.andExpect(jsonPath("$.resource.days[3].day", is(4)))
    	.andExpect(jsonPath("$.resource.days[3].total", is("08:24:00")))
    	.andExpect(jsonPath("$.resource.days[3].hours[0]", is("04:30:00")))
    	.andExpect(jsonPath("$.resource.days[3].hours[1]", is("08:30:00")))
    	.andExpect(jsonPath("$.resource.days[3].hours[2]", is("09:40:00")))
    	.andExpect(jsonPath("$.resource.days[3].hours[3]", is("13:40:00")));
    	// .andDo(print());
    	verify(clockService, times(1)).findByUserAndPeriod(12345l, 4, 2018);
    	verifyNoMoreInteractions(clockService);
    	verify(userService, times(1)).get(12345l);
    	verifyNoMoreInteractions(userService);
    }
	
	private List<Clock> getClocks() {
		List<Clock> clock = new ArrayList<>();
		
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 1, 8, 1)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 1, 12, 0)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 1, 13, 10)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 1, 17, 11)));
		
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 2, 8, 5)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 2, 12, 5)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 2, 13, 0)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 2, 17, 10)));
		
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 3, 8, 0)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 3, 12, 10)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 3, 13, 10)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 3, 17, 10)));

		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 4, 4, 30)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 4, 8, 30)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 4, 9, 40)));
		clock.add(new Clock(null, LocalDateTime.of(2018, 4, 4, 13, 40)));
		
		return clock;
	}
}
