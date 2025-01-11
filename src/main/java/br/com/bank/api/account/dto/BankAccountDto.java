package br.com.bank.api.account.dto;

import br.com.bank.api.account.enums.AccountStatus;
import br.com.bank.api.account.enums.AccountType;
import br.com.bank.api.account.enums.HolderType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class BankAccountDto {

    public enum Request{;

        @Data
        public static class BankAccount{
            @NotNull
            private String branch;
            @NotNull
            private AccountType type;
            @NotNull
            private String holderName;
            @NotNull
            private String holderEmail;
            @NotNull
            private String holderDocument;
            @NotNull
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
