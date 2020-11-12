package com.empresa.domain.exception;

public abstract class EntidadeNaoEcontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEcontradaException(String mensagem) {
		super(mensagem);
	}
}
