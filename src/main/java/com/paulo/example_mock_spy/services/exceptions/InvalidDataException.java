package com.paulo.example_mock_spy.services.exceptions;

public class InvalidDataException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidDataException(String msg) {
		super(msg);
	}
}
