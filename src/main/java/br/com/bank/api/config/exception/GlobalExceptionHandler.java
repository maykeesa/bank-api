package br.com.bank.api.config.exception;

import br.com.bank.api.config.exception.exceptions.AccountConflictException;
import br.com.bank.api.config.exception.exceptions.AccountCounterPartyConflictException;
import br.com.bank.api.utils.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto.Body.ResponseError> handlerMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> map = new HashMap<>();
        var fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(
                new ResponseDto.Body.ResponseError(BAD_REQUEST.value(), ex.getClass().getSimpleName(), map));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto.Body.ResponseError> handlerEntityNotFound(EntityNotFoundException ex){
        return ResponseEntity.status(NOT_FOUND).body(
                new ResponseDto.Body.ResponseError(NOT_FOUND.value(), ex.getClass().getSimpleName(), ex.getMessage()));
    }

    @ExceptionHandler(AccountConflictException.class)
    public ResponseEntity<ResponseDto.Body.ResponseError> handlerAccountConflict(AccountConflictException ex){
        return ResponseEntity.status(BAD_REQUEST).body(
                new ResponseDto.Body.ResponseError(BAD_REQUEST.value(), ex.getClass().getSimpleName(), ex.getMessage()));
    }

    @ExceptionHandler(AccountCounterPartyConflictException.class)
    public ResponseEntity<ResponseDto.Body.ResponseError> handlerAccountCounterPartyConflict(
            AccountCounterPartyConflictException ex){

        return ResponseEntity.status(BAD_REQUEST).body(
                new ResponseDto.Body.ResponseError(BAD_REQUEST.value(), ex.getClass().getSimpleName(), ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto.Body.ResponseError> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(
                new ResponseDto.Body.ResponseError(BAD_REQUEST.value(), ex.getClass().getSimpleName(), ex.getMessage()));
    }
}
