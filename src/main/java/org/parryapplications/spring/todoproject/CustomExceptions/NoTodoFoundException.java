package org.parryapplications.spring.todoproject.CustomExceptions;

public class NoTodoFoundException extends RuntimeException {
    public NoTodoFoundException (String errorMsg){
        super(errorMsg);
    }
}
