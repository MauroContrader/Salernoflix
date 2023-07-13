package Mauro.Salernoflix.Config;

import Mauro.Salernoflix.exception.ResponseError;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ErrorHandlerConfig extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseError> handleGerericError(Exception ex) {
        ResponseError body = new ResponseError(ex.getMessage());
        Logger.getLogger(ErrorHandlerConfig.class.getSimpleName()).log(Level.SEVERE, ExceptionUtils.getStackTrace(ex));
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
