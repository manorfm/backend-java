package br.com.timer.rest.services;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.timer.domain.clock.Clock;
import br.com.timer.rest.resources.PageResources;

public abstract class AbstractController {

    public ResponseEntity<String> responseOk() {
        return response(HttpStatus.OK);
    }
    
    public ResponseEntity<String> responseCreated() {
    	return response(HttpStatus.CREATED);
    }
    
    public <T> ResponseEntity<T> responseCreated(T t) {
    	return response(t, HttpStatus.CREATED);
    }
    
    public <T> ResponseEntity<T> responseOk(T t) {
        return response(t, HttpStatus.OK);
    }
    
    public <T> ResponseEntity<T> responseError(HttpStatus status) {
    	return response(status);
    }

    public <T> ResponseEntity<T> responseError(T t,HttpStatus status) {
    	return response(t, status);
    }
    
    public <T> ResponseEntity<PageResources<T>> responseError(HttpStatus status, String... messages) {
    	PageResources<T> page = new PageResources<>();
    	
    	if (messages.length > 0) {
    		Arrays.asList(messages)
    			.stream()
    			.forEach(message -> page.addError("Error", message));
    	}
    	return response(page, status);
    }
    
    private <T> ResponseEntity<T> response(HttpStatus status) {
    	return new ResponseEntity<T>(status);
    }
    
    public <T> ResponseEntity<T> response(T t, HttpStatus status) {
    	return new ResponseEntity<T>(t, status);
    }
    
    protected String toString(Object o) {
    	if (o == null) {
    		return null;
    	}
    	
    	return String.valueOf(o);
    }
    
    protected ResponseEntity<PageResources<?>> exception(String title, Exception e) {
		PageResources<Clock> pageResource = new PageResources<>();
		pageResource.addError(title, e.getMessage());
		return responseError(pageResource, HttpStatus.CONFLICT);
	}
}
