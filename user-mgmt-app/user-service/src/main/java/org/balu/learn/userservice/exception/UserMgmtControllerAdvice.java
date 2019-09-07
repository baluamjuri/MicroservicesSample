package org.balu.learn.userservice.exception;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserMgmtControllerAdvice {

	private static final Logger logger = LogManager.getLogger(UserMgmtControllerAdvice.class);
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleSQLException(DataIntegrityViolationException ex){
		logger.error(ex);
		return ResponseEntity.unprocessableEntity().build();
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex){
		logger.error(ex);
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex){
		logger.error(ex);
		return ResponseEntity.unprocessableEntity().build();
	}
}
