package br.com.bank.api.config.exception.exceptions;

public class AccountCounterPartyConflictException extends RuntimeException{

    public AccountCounterPartyConflictException(String message) {
        super(message);
    }
}
