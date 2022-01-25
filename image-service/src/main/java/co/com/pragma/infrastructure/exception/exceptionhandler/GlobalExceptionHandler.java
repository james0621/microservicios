package co.com.pragma.infrastructure.exception.exceptionhandler;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import co.com.pragma.infrastructure.exception.DataNoFoundException;
import co.com.pragma.infrastructure.exception.message.ErrorMessage;
import co.com.pragma.util.Message;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final ConcurrentHashMap<String, Integer> CODIGOS_ESTADO = new ConcurrentHashMap<>();

	public GlobalExceptionHandler() {
		CODIGOS_ESTADO.put(InvalidDataAccessResourceUsageException.class.getSimpleName(), 1);
		CODIGOS_ESTADO.put(DataIntegrityViolationException.class.getSimpleName(), 2);
		CODIGOS_ESTADO.put(DataNoFoundException.class.getSimpleName(), 3);
		CODIGOS_ESTADO.put(MethodArgumentNotValidException.class.getSimpleName(), 4);
		CODIGOS_ESTADO.put(BindException.class.getSimpleName(), 5);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorMessage> handlerAllExcption(Exception e, HttpServletRequest request) {
		ResponseEntity<ErrorMessage> message;

		String exceptionName = e.getClass().getSimpleName();
		Integer code = CODIGOS_ESTADO.get(exceptionName);
		String errorMessage = "";
		ErrorMessage error;

		if (code != null && code <= 3) {
			switch (code) {
				case 1:
					errorMessage = Message.NOT_FOUND_ATRIBUTE;
					break;
				case 2:
					errorMessage = Message.DATA_DUPLICATE;
					break;
				case 3:
					errorMessage = e.getMessage();
					break;
			}
			error = new ErrorMessage(errorMessage, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
			message = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		} else if (code != null && code > 3) {
			error = errorFields(e, request.getRequestURI(), exceptionName);
			message = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		} else {
			error = new ErrorMessage(Message.CONTACT_ADMINISTRATOR, HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
			message = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return message;
	}


	public ErrorMessage errorFields(Exception e, String uri, String nameException) {
		BindingResult result = null;
		if (nameException.equals(e.getClass().getSimpleName())) {
			MethodArgumentNotValidException mae = (MethodArgumentNotValidException) e;
			result = mae.getBindingResult();
		}
		List<FieldError> fieldErrors = result.getFieldErrors();
		StringBuilder message = new StringBuilder();
		fieldErrors.forEach(field -> message.append(field.getField() + ": " + field.getDefaultMessage() + ". "));
		ErrorMessage error = new ErrorMessage(message.toString(), HttpStatus.BAD_REQUEST.value(),
				uri);
		return error;
	}
}
