package org.parryapplications.spring.todoproject.aop;

import org.parryapplications.spring.todoproject.dto.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerAdviceHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultSet<String>> validationHandler(MethodArgumentNotValidException notValidException) {
        logger.error("Validation Failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultSet<>("Validation Failed :: " + notValidException, notValidException.getErrorCount()));
    }

//    @ExceptionHandler(JOSEException.class)
//    public ResponseEntity<String> unauthorizedUserException(JOSEException e) {
//        logger.error("Unauthorized User : ", e);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized User, Access Denied");
//    }
//
//    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
//    public ResponseEntity<String> forbiddenAccessDeniedException(HttpClientErrorException.Forbidden e) {
//        logger.error("Forbidden, Access Denied : ", e);
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
//    }
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<String> accessDeniedException(AccessDeniedException e) {
//        logger.error("Access Denied: ", e);
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
//    }

}
