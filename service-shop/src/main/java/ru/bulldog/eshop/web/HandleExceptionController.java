package ru.bulldog.eshop.web;

import ru.bulldog.eshop.errors.EntityValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.bulldog.eshop.dto.ErrorDTO;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class HandleExceptionController {

	private static final Logger logger = LogManager.getLogger(HandleExceptionController.class);

	@ExceptionHandler
	public ResponseEntity<ErrorDTO> onEntityNotFound(EntityNotFoundException ex) {
		return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorDTO> onBadCredentials(BadCredentialsException ex) {
		return new ResponseEntity<>(new ErrorDTO("Invalid username or password"), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorDTO> onValidationError(EntityValidationException ex) {
		return new ResponseEntity<>(new ErrorDTO(ex.getErrors()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorDTO> onServerError(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
