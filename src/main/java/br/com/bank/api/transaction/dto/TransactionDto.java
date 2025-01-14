package br.com.bank.api.transaction.dto;

import br.com.bank.api.account.dto.BankAccountDto;
import br.com.bank.api.account.enums.AccountType;
import br.com.bank.api.account.enums.HolderType;
import br.com.bank.api.transaction.enums.TransactionType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionDto {

    public enum Request{;

        @Data
        public static class Transaction{
            @Pattern(regexp = "CREDIT|DEBIT|AMOUNT_HOLD|AMOUNT_RELEASE", message = "The value of the type field can " +
                    "only be CREDIT, DEBIT, AMOUNT_HOLD or AMOUNT_RELEASE")
            @NotBlank(message = "The field cannot be null or blank")
            private String type;

            @NotNull(message = "The field cannot be null")
            @PositiveOrZero(message = "The amount cannot be negative")
            @Digits(integer = 16, fraction = 2, message = "The amount must have up to 16 digits before and 2 digits " +
                    "after the decimal point")
            private BigDecimal amount;

            @org.hibernate.validator.constraints.UUID(message = "The Id field must be a UUID")
            @NotNull(message = "The field cannot be null")
            private String bankAccountId;

            @Size(max = 3, message = "The counter party bank code can be up to 3 characters long")
            @NotBlank(message = "The field cannot be null or blank")
            private String counterPartyBankCode;

            @NotBlank(message = "The field cannot be null or blank")
            private String counterPartyBankName;

            @Size(max = 5, message = "The counter party branch can be up to 3 characters long")
            @NotBlank(message = "The field cannot be null or blank")
            private String counterPartyBranch;

            @Size(max = 10, message = "The counter party account number can be up to 3 characters long")
            @NotBlank(message = "The field cannot be null or blank")
            private String counterAccountNumber;

            @Pattern(regexp = "PAYMENT|CURRENT|SAVINGS|SALARY", message = "The value of the status field can only be " +
                    "PAYMENT, CURRENT, SAVINGS or SALARY")
            @NotBlank(message = "The field cannot be null or blank")
            private String counterAccountType;

            @NotBlank(message = "The field cannot be null or blank")
            @Size(max = 200, message = "The holder's name can be up to 200 characters long")
            private String counterHolderName;

            @Pattern(regexp = "NATURAL|LEGAL", message = "The value of the status field can only be NATURAL or LEGAL")
            @NotBlank(message = "The field cannot be null or blank")
            private String counterHolderType;

            @NotBlank(message = "The field cannot be null or blank")
            private String counterHolderDocument;
        }

    }

    public enum Response{;

        @Data
        public static class Transaction{
            private UUID id;
            private TransactionType type;
            private BigDecimal amount;
            private BankAccountDto.Response.BankAccount bankAccountId;
            private String counterPartyBankCode;
            private String counterPartyBankName;
            private String counterPartyBranch;
            private String counterAccountNumber;
            private AccountType counterAccountType;
            private String counterHolderName;
            private HolderType counterHolderType;
            private String counterHolderDocument;
            private LocalDateTime createdAt;
            private LocalDateTime updateAt;
        }

    }

}
