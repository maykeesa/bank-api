package br.com.bank.api.transaction.dto;

import br.com.bank.api.account.BankAccount;
import br.com.bank.api.account.enums.AccountType;
import br.com.bank.api.account.enums.HolderType;
import br.com.bank.api.transaction.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionDto {

    public enum Response{;

        @Data
        public static class Transaction{
            private UUID id;
            private TransactionType type;
            private BigDecimal amount;
            private BankAccount bankAccountId;
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
