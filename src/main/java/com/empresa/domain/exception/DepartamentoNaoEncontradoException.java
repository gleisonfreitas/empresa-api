package com.empresa.domain.exception;

public class DepartamentoNaoEncontradoException extends EntidadeNaoEcontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DepartamentoNaoEncontradoException(String message) {
		super(message);
	}

	public DepartamentoNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de departamento com id %d.", id));
	}

}
