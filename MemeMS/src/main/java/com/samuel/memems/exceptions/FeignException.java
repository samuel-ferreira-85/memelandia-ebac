package com.samuel.memems.exceptions;

public class FeignException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public FeignException(String mensagem) {
		super(mensagem);
	}

}
