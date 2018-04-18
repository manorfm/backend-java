package br.com.timer.rest.domain.user;

import static org.hamcrest.Matchers.hasSize;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
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

import br.com.timer.domain.user.User;
import br.com.timer.domain.user.service.UserService;
import br.com.timer.rest.domain.user.assembler.UserResourcesAssembler;
import br.com.timer.rest.domain.user.controller.UserController;

public class UserControllerTest {

	private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Spy
    private UserResourcesAssembler userAssembler;

    @InjectMocks
    private UserController userController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }
    
    @Test
    public void testFindUsers() throws Exception {
    	User user = new User();
    	user.setPis(12345l);
    	user.setName("Manoel Medeiros");
    	
        List<User> users = Arrays.asList(user);
        
        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.resources", hasSize(1)))
                .andExpect(jsonPath("$.resources[0].pis", is(12345)))
                .andExpect(jsonPath("$.resources[0].name", is("Manoel Medeiros")))
                .andDo(print());
        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testFindUsersOne() throws Exception {
    	User user = new User();
    	user.setPis(12345l);
    	user.setName("Manoel Medeiros");
    	
    	when(userService.get(12345l)).thenReturn(user);
    	mockMvc.perform(get("/users/12345"))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    	.andExpect(jsonPath("$.resource", notNullValue()))
    	.andExpect(jsonPath("$.resource.pis", is(12345)))
    	.andExpect(jsonPath("$.resource.name", is("Manoel Medeiros")))
    	.andDo(print());
    	verify(userService, times(1)).get(12345l);
    	verifyNoMoreInteractions(userService);
    }
}
