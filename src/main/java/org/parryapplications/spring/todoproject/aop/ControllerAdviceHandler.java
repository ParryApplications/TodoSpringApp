package org.parryapplications.spring.todoproject.aop;

import com.nimbusds.jose.JOSEException;
import org.parryapplications.spring.todoproject.dto.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ControllerAdviceHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerAdviceHandler.class);

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResultSet<String>> resourceNotFoundException(NoResourceFoundException e) {
        logger.error("Resource Not Found : ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultSet<>("Resource Not Found :: " + e.getMessage(), 1));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultSet<String>> validationHandler(MethodArgumentNotValidException notValidException) {
        logger.error("Validation Failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultSet<>("Validation Failed :: " + notValidException, notValidException.getErrorCount()));
    }

    @ExceptionHandler(JOSEException.class)
    public ResponseEntity<String> unauthorizedUserException(JOSEException e) {
        logger.error("Unauthorized User : ", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized User, Access Denied");
    }
//
//    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
//    public ResponseEntity<String> forbiddenAccessDeniedException(HttpClientErrorException.Forbidden e) {
//        logger.error("Forbidden, Access Denied : ", e);
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
//    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> accessDeniedException(AccessDeniedException e) {
        logger.error("Access Denied: ", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }

//    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
//    public ResponseEntity<String> unauthorizedException(HttpClientErrorException.Unauthorized e) {
//        logger.error("Unauthorized: ", e);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//    }

}
