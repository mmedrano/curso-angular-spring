package com.bitonelabs.clientesapi.exception;

public class ClienteNoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ClienteNoEncontradoException(String msg) {
		super(msg);
	}

}
