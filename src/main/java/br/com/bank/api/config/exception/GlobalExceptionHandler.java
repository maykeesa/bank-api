package br.com.bank.api.config.exception;

import br.com.bank.api.core.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<ResponseDto.Body.Response> handlerMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> map = new HashMap<>();
        var fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(new ResponseDto.Body.Response(BAD_REQUEST.value(), map));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto.Body.Response> handlerEntityNotFound(EntityNotFoundException ex){
        return ResponseEntity.status(NOT_FOUND).body(new ResponseDto.Body.Response(NOT_FOUND.value(), ex.getMessage()));
    }
}
