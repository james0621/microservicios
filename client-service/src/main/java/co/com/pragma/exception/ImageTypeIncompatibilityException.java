package co.com.pragma.exception;

import co.com.pragma.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ImageTypeIncompatibilityException extends RuntimeException{
	
	public ImageTypeIncompatibilityException() {
		super(Message.IMAGE_TYPE_NOT_FOUND);
	}

}
