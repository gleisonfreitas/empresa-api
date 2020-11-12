package com.empresa.domain.exception;

public class CargoNaoEncontradoException extends EntidadeNaoEcontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CargoNaoEncontradoException(String message) {
		super(message);
	}

	public CargoNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de cargo com código %d.", id));
	}

}
