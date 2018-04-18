package br.com.timer.rest.domain.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.timer.domain.user.User;
import br.com.timer.domain.user.service.UserService;
import br.com.timer.rest.domain.user.assembler.UserResourcesAssembler;
import br.com.timer.rest.domain.user.resources.UserResource;
import br.com.timer.rest.resources.PageResources;
import br.com.timer.rest.services.AbstractController;

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
		} catch (Exception e) {
			PageResources<User> pageResource = new PageResources<>();
			pageResource.addError("Create user error!", e.getMessage());
			return responseError(pageResource, HttpStatus.CONFLICT);
		}
		
		return responseCreated();
	}

	@RequestMapping(value = "/{pis}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable Long pis) {
		User user = userService.get(pis);
		
		PageResources<UserResource> pageResource = new PageResources<>();
		if (user == null) {
			pageResource.addError("User not found!", String.format("User with pis: %d not found", pis));
			return responseError(pageResource, HttpStatus.CONFLICT);
		}
		
		UserResource resource = userAssembler.convert(user);
		pageResource.setResource(resource);
		
		return response(pageResource, HttpStatus.OK);
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
		} catch (Exception e) {
			PageResources<UserResource> pageResource = new PageResources<>();
			pageResource.addError("User found!", String.format("User with pis: %d not found", pis));
			return responseError(pageResource, HttpStatus.CONFLICT);
		}
		
		return responseOk();
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody UserResource resource) {
		User user = userService.get(resource.getPis());
		
		PageResources<UserResource> pageResource = new PageResources<>();
		if (user == null) {
			pageResource.addError("User not found!", String.format("User with pis: %d not found", resource.getPis()));
			return responseError(pageResource, HttpStatus.CONFLICT);
		}
		
		user.setName(resource.getName());
		user.setPassword(resource.getPassword());
		userService.save(user);
		
		return response(pageResource, HttpStatus.OK);
	}
}