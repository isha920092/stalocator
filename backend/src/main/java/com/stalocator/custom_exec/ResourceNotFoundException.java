package com.stalocator.custom_exec;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException(String mesg) {
		super(mesg);
	}
	
}
