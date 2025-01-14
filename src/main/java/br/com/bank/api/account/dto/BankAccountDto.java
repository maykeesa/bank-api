package br.com.bank.api.account.dto;

import br.com.bank.api.account.enums.AccountStatus;
import br.com.bank.api.account.enums.AccountType;
import br.com.bank.api.account.enums.HolderType;
import br.com.bank.api.balance.dto.BalanceDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class BankAccountDto {

    public enum Request{;

        @Data
        public static class Register{
            @Size(max = 5, message = "The branch can be up to 5 characters long")
            @NotBlank(message = "The field cannot be null or blank")
            private String branch;

            @Pattern(regexp = "PAYMENT|CURRENT|SAVINGS|SALARY", message = "The value of the status field can only be " +
                    "PAYMENT, CURRENT, SAVINGS or SALARY")
            @NotBlank(message = "The field cannot be null or blank")
            private String type;

            @NotBlank(message = "The field cannot be null or blank")
            @Size(max = 200, message = "The holder's name can be up to 200 characters long")
            private String holderName;

            @Email(message = "The field must have the format of an email")
            @NotBlank(message = "The field cannot be null or blank")
            @Size(max = 200, message = "The holder's email can be up to 200 characters long")
            private String holderEmail;

            @NotBlank(message = "The field cannot be null or blank")
            private String holderDocument;

            @Pattern(regexp = "NATURAL|LEGAL", message = "The value of the status field can only be NATURAL or LEGAL")
            @NotBlank(message = "The field cannot be null or blank")
            private String holderType;
        }

        @Data
        public static class Update{
            @Email(message = "The field must have the format of an email")
            @Size(max = 200, message = "The holder's email can be up to 200 characters long")
            private String holderEmail;

            @Pattern(regexp = "ACTIVE|BLOCKED|FINISHED|null", message = "The value of the status field can only be ACTIVE, " +
                    "BLOCKED or FINISHED")
            private String status;
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
            private BalanceDto.Response.BalanceWithoutId balance;
            private LocalDateTime createdAt;
            private LocalDateTime updateAt;
        }
    }
}
