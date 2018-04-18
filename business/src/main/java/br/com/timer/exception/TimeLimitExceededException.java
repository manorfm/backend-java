package br.com.timer.exception;

public class TimeLimitExceededException extends RuntimeException {

	private static final long serialVersionUID = -1465396772704499013L;

	public TimeLimitExceededException(String message) {
		super(message);
	}
}
