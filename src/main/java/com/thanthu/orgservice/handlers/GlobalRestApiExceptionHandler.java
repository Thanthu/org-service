package com.thanthu.orgservice.handlers;

import java.util.Arrays;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.thanthu.orgservice.dtos.ErrorResponseDto;
import com.thanthu.orgservice.exceptions.NotFoundException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class GlobalRestApiExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ErrorResponseDto handler(NotFoundException e) {
		log.error(e.getMessage(), e);
		return ErrorResponseDto.builder().code(404).messages(Arrays.asList(e.getMessage())).build();
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponseDto handler(RuntimeException e) {
		log.error(e.getMessage(), e);
		return ErrorResponseDto.builder().code(500).messages(Arrays.asList(e.getMessage())).build();
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponseDto handler(Exception e) {
		log.error(e.getMessage(), e);
		return ErrorResponseDto.builder().code(500).messages(Arrays.asList(e.getMessage())).build();
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResponseDto handler(ConstraintViolationException e) {
		log.error(e.getMessage(), e);
		return ErrorResponseDto.builder().code(400).messages(Arrays.asList(e.getMessage())).build();
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResponseDto handler(MissingServletRequestParameterException e) {
		log.error(e.getMessage(), e);
		return ErrorResponseDto.builder().code(400).messages(Arrays.asList(e.getMessage())).build();
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResponseDto handler(MethodArgumentTypeMismatchException e) {
		log.error(e.getMessage(), e);
		return ErrorResponseDto.builder().code(400).messages(Arrays.asList(e.getMessage())).build();
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResponseDto handler(HttpRequestMethodNotSupportedException e) {
		log.error(e.getMessage(), e);
		return ErrorResponseDto.builder().code(400).messages(Arrays.asList(e.getMessage())).build();
	}
	
}
