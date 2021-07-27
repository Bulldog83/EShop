package ru.bulldog.eshop.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.bulldog.eshop.dto.ErrorDTO;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class HandleExceptionController {

	private final static Logger logger = LogManager.getLogger(HandleExceptionController.class);

	@ExceptionHandler
	public ResponseEntity<?> onEntityNotFound(EntityNotFoundException ex) {
		return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<?> onServerError(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
