package org.sbsplus.general.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
public class ExceptionHandleController {
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String methodArgumentTypeMismatchException(HttpServletRequest request){
        request.setAttribute("msg", "올바르지 않은 요청입니다.");
        return "/exception/error";
    }
    
    
    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFoundException(HttpServletRequest request, EntityNotFoundException exception){
        request.setAttribute("msg", exception.getMessage());
        return "/exception/error";
    }
    

}
