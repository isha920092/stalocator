package com.stalocator.exc_handler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.stalocator.custom_exec.ResourceNotFoundException;
import com.stalocator.dto.ApiResponse;


@RestControllerAdvice 
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		System.out.println("in method arg invalid " + e);
		System.out.println("global errs "+e.getGlobalErrors());
		System.out.println("field errs "+e.getFieldErrors());
			List<FieldError> fieldErrors = e.getFieldErrors();// list of fiels having validation errs
		
		Map<String, String> map = fieldErrors.stream()
				.collect(
						Collectors.toMap(FieldError::getField, 
								FieldError::getDefaultMessage));
		return ResponseEntity.
				status(HttpStatus.BAD_REQUEST).body(map);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ApiResponse handleResourceNotFoundException(
			ResourceNotFoundException e) {
		System.out.println("in res not found " + e);
		return new ApiResponse(e.getMessage());
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse handleAnyException(RuntimeException e) {
		System.out.println("in catch-all " + e);
		e.printStackTrace();
		return new ApiResponse(e.getMessage());
	}
}