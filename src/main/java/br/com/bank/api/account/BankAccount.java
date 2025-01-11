package br.com.bank.api.account;

import br.com.bank.api.account.enums.AccountStatus;
import br.com.bank.api.account.enums.AccountType;
import br.com.bank.api.account.enums.HolderType;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String branch;
    private String number;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    private String holderName;
    private String holderEmail;
    private String holderDocument;
    @Enumerated(EnumType.STRING)
    private HolderType holderType;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Nullable
    private LocalDateTime updateAt;

    public BankAccount(){

    }
}
