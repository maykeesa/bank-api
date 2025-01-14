package br.com.bank.api.config.exception.exceptions;

public class AccountConflictException extends RuntimeException{

    public AccountConflictException(String message) {
        super(message);
    }

}
