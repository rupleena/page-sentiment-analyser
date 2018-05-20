package analyser.controller;

import analyser.dto.Exception;
import analyser.util.ValidationUtil;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Exception> invalidInput(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Exception response = new Exception();
        response.setErrorCode("Validation Error");
        response.setErrorMessage("Invalid inputs.");
        response.setErrors(ValidationUtil.fromBindingErrors(result));
        return new ResponseEntity<Exception>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<Exception> invalidArgument(InvalidArgumentException ex) {
        String errorCode = "Invalid Argument Error ";
        return getResponseEntity(errorCode, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Exception> resourceNotFound(NoSuchElementException ex) {
        String errorCode = "Not Found";
        return getResponseEntity(errorCode, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public ResponseEntity<Exception> resourceNotFound(AsyncRequestTimeoutException ex) {
        String errorCode = "Request Timed Out";
        return getResponseEntity(errorCode, ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Exception> resourceNotFound(UnsupportedOperationException ex) {
        String errorCode = "Page Access Operation Not Supported";
        return getResponseEntity(errorCode, ex.getMessage(), HttpStatus.FORBIDDEN);
    }


    private ResponseEntity<Exception> getResponseEntity(String errorCode, String errorMessage, HttpStatus status) {
        Exception response = new Exception();
        response.setErrorCode(errorCode);
        if (errorMessage != null) {
            response.setErrorMessage(errorMessage);
        }
        return new ResponseEntity<Exception>(response, status);

    }
}
