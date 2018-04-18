package br.com.timer.exception;

import lombok.Getter;

public class BusinessExeception extends RuntimeException {

	private static final long serialVersionUID = 3933963804152709226L;

	@Getter
	private BusinessMessage businessMessage;
	
	public BusinessExeception(BusinessMessage message) {
		super(message.getMessage());
		this.businessMessage = message;
	} 

	public BusinessExeception(BusinessMessage message, Throwable e) {
		super(message.getMessage(), e);
		this.businessMessage = message;
	}
}
