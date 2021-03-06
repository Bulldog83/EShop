package ru.bulldog.eshop.web;

import org.springframework.security.web.firewall.RequestRejectedException;
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
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class HandleExceptionController {

	private final static Logger logger = LogManager.getLogger(HandleExceptionController.class);

	@ExceptionHandler
	public ResponseEntity<?> onEntityNotFound(EntityNotFoundException ex) {
		return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<?> onBadCredentials(BadCredentialsException ex) {
		return new ResponseEntity<>(new ErrorDTO("Invalid username or password"), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler
	public ResponseEntity<?> onAccessDenied(AccessDeniedException ex) {
		return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler
	public ResponseEntity<?> onValidationError(EntityValidationException ex) {
		return new ResponseEntity<>(new ErrorDTO(ex.getErrors()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<?> onRequestRejected(RequestRejectedException ex) {
		return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<?> onServerError(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
