package com.empresa.domain.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NegocioException(String message) {
		super(message);
	}

	public NegocioException(String mensagem, Throwable cause) {
		super(mensagem, cause);
	}
}
