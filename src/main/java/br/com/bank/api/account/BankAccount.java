package br.com.bank.api.account;

import br.com.bank.api.account.enums.AccountStatus;
import br.com.bank.api.account.enums.AccountType;
import br.com.bank.api.account.enums.HolderType;
import br.com.bank.api.balance.Balance;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "bank_accounts", schema = "bank")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 5)
    private String branch;
    @Column(length = 10)
    private String number;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @Column(length = 200)
    private String holderName;
    @Column(length = 200)
    private String holderEmail;
    private String holderDocument;
    @Enumerated(EnumType.STRING)
    private HolderType holderType;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @OneToOne(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Balance balance;

    public BankAccount(){

    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", branch='" + branch + '\'' +
                ", number='" + number + '\'' +
                ", type=" + type +
                ", holderName='" + holderName + '\'' +
                ", holderEmail='" + holderEmail + '\'' +
                ", holderDocument='" + holderDocument + '\'' +
                ", holderType=" + holderType +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                ", balance={" +
                    "bankAccountId=" + balance.getBankAccountId() +
                    ", availableAmount=" + balance.getAvailableAmount() +
                    ", blockedAmount=" + balance.getBlockedAmount() +
                    '}' +
                '}';
    }
}
