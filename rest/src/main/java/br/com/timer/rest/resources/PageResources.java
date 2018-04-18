package br.com.timer.rest.resources;

import java.util.ArrayList;
import java.util.List;

import br.com.timer.exception.BusinessExeception;
import br.com.timer.exception.BusinessMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageResources<T> {
	
	private List<BusinessMessage> errors;
	private List<BusinessMessage> warnings;
	
	private List<T> resources;
	private T resource;
	
	public PageResources(T resource) {
		this.resource = resource;
	}

	public PageResources(List<T> resources) {
		this.resources = resources;
	}
	
	public void addError(BusinessExeception exception) {
		addError(exception.getBusinessMessage());
	}
	
	public void addError(String title, String message) {
		addError(new BusinessMessage(title, message));
	}
	
	public void addError(BusinessMessage erro) {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		errors.add(erro);
	}
	
	public void addWarning(BusinessMessage warning) {
		if (warnings == null) {
			warnings = new ArrayList<>();
		}
		warnings.add(warning);
	}
	
	public void addWarning(BusinessExeception exception) {
		addWarning(exception.getBusinessMessage());
	}
	
	public void addWarning(String title, String warning) {
		addWarning(new BusinessMessage(title, warning));
	}
}
