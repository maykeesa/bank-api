package br.com.bank.api.account.dto;

import br.com.bank.api.account.enums.AccountStatus;
import br.com.bank.api.account.enums.AccountType;
import br.com.bank.api.account.enums.HolderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class BankAccountDto {

    public enum Request{;

        @Data
        public static class BankAccount{
            @NotBlank(message = "The field cannot be null or blank")
            private String branch;

            @NotNull(message = "The field cannot be null")
            private AccountType type;

            @NotBlank(message = "The field cannot be null or blank")
            @Size(max = 200, message = "The holder's name can be up to 200 characters long")
            private String holderName;

            @Email
            @NotBlank(message = "The field cannot be null or blank")
            @Size(max = 200, message = "The holder's name can be up to 200 characters long")
            private String holderEmail;

            @NotBlank(message = "The field cannot be null or blank")
            private String holderDocument;

            @NotNull(message = "The field cannot be null")
            private HolderType holderType;
        }
    }

    public enum Response{;

        @Data
        public static class BankAccount{
            private UUID id;
            private String branch;
            private String number;
            private AccountType type;
            private String holderName;
            private String holderEmail;
            private String holderDocument;
            private HolderType holderType;
            private AccountStatus status;
            private LocalDateTime createdAt;
            private LocalDateTime updateAt;
        }
    }
}
