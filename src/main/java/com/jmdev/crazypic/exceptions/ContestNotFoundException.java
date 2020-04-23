package com.jmdev.crazypic.exceptions;

public class ContestNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7225306293711154561L;

	public ContestNotFoundException(int id) {
		super(String.format("Unknown contest with id : '%s'", id));
	}
}
