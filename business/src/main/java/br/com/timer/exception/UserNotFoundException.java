package br.com.timer.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5186765795932546148L;

	public UserNotFoundException(Long pis) {
		super(String.format("User with pis: %d not found", pis));
	}
}
