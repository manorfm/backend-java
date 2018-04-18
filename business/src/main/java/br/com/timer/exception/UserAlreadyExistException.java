package br.com.timer.exception;

public class UserAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 5186765795932546148L;

	public UserAlreadyExistException(Long pis) {
		super(String.format("User with pis: %d already registred.", pis));
	}
}
