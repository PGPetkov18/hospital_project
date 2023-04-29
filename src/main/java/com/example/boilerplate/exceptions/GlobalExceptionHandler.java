package com.example.boilerplate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ModelAndView handleUsernameAlreadyTakenException(UsernameAlreadyTakenException exception){
        return new ModelAndView("custom-error").addObject("message","Потребителско име вече е заето");
    }
@ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ModelAndView handleInvalidCredentialsException(InvalidCredentialsException exception){
        return new ModelAndView("custom-error").addObject("message","\n" +
                "Невалидни идентификационни данни ");
}
    @ExceptionHandler(UserDoesNotHavePermissionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ModelAndView handleUserDoesNotHavePermissionException(UserDoesNotHavePermissionException userDoesNotHavePermissionException) {

        return new ModelAndView("custom-error")
                .addObject("message", "Не сте оторизирани да посещавате тази страница! Моля впишете се и пробвайте пак?");
    }



}
