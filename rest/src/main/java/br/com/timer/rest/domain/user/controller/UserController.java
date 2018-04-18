package br.com.timer.rest.domain.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.timer.domain.user.User;
import br.com.timer.domain.user.service.UserService;
import br.com.timer.exception.UserAlreadyExistException;
import br.com.timer.exception.UserNotFoundException;
import br.com.timer.rest.domain.user.assembler.UserResourcesAssembler;
import br.com.timer.rest.domain.user.resources.UserResource;
import br.com.timer.rest.resources.PageResources;
import br.com.timer.rest.services.AbstractController;
import br.com.timer.security.KeyUtil;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserResourcesAssembler userAssembler;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody UserResource resource) {
		try {
			User user = userAssembler.convert(resource);
			userService.save(user);
		} catch (UserAlreadyExistException e) {
			return exception("Create user error!", e);
		}
		
		return responseCreated();
	}

	@RequestMapping(value = "/{pis}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable Long pis) {
		try {
			User user = userService.get(pis);
			PageResources<UserResource> pageResource = new PageResources<>();
			UserResource resource = userAssembler.convert(user);
			pageResource.setResource(resource);
		
			return response(pageResource, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return exception("User not exist", e);
		}
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getUsers() {
		List<User> users = userService.findAll();
		
		PageResources<UserResource> pageResource = new PageResources<>();
		List<UserResource> resources = userAssembler.convert(users);
		pageResource.setResources(resources);
		
		return response(pageResource, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{pis}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable Long pis) {
		try {
			userService.remove(pis);
			return responseOk();
		} catch (Exception e) {
			return exception("Erro removing", e);
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody UserResource resource) {
		try {
			User user = userService.get(resource.getPis());
		
			PageResources<UserResource> pageResource = new PageResources<>();
		
			user.setName(resource.getName());
			user.setPassword(resource.getPassword());
			userService.save(user);
			return response(pageResource, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return exception("Erro updating.", e);
		}
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam String login, @RequestParam String password) {
		User user = userService.login(login, password);
		
		if (user == null) {
			PageResources<User> page = new PageResources<>();
			page.addError("Authentication failed", "User or password not match");
			return responseError(page, HttpStatus.CONFLICT);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			String token = KeyUtil.encodeBase64(user.getName() + ":" + user.getPassword());
			
			map.put("token", token);
			map.put("user", userAssembler.convert(user));
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
	}
}