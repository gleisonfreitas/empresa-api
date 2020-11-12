package com.empresa.api.validation;

import org.springframework.validation.BindingResult;

public class ValidacaoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BindingResult bindingResult;
	
	public ValidacaoException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

	/**
	 * @return the bindingResult
	 */
	public BindingResult getBindingResult() {
		return bindingResult;
	}

}
