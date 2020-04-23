package com.jmdev.crazypic.exceptions;

public class PictureNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7225306293711154561L;

	public PictureNotFoundException(int id) {
		super(String.format("Unknown picture with id : '%s'", id));
	}
}
