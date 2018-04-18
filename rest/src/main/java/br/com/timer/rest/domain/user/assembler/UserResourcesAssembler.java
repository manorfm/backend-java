package br.com.timer.rest.domain.user.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.timer.domain.user.User;
import br.com.timer.rest.domain.user.resources.UserResource;
import br.com.timer.rest.services.AbstractAssembler;

@Component
public class UserResourcesAssembler extends AbstractAssembler<User, UserResource> {
   
	public List<UserResource> convert(List<User> users) {
    	return users.stream()
			.map(user -> mapper.map(user, UserResource.class))
			.collect(Collectors.toList());
    }
    
    public UserResource convert(User user) {
    	/*MappingContext context = new MappingContext.Factory().getContext();
    	context.setProperty("password", null);*/
        return mapper.map(user, UserResource.class/*, context*/);
    }
    
    public User convert(UserResource resource) {
    	return mapper.map(resource, User.class);
    }
    
}
