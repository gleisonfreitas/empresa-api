package com.empresa.domain.exception;

public class FuncionarioNaoEncontradoException extends EntidadeNaoEcontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FuncionarioNaoEncontradoException(String message) {
		super(message);
	}

	public FuncionarioNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de funcionário com código %d.", id));
	}

}
