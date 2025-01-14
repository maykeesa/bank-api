package br.com.bank.api.transaction;

import br.com.bank.api.account.BankAccount;
import br.com.bank.api.account.enums.AccountType;
import br.com.bank.api.account.enums.HolderType;
import br.com.bank.api.transaction.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions", schema = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Column(precision = 16, scale = 2)
    private BigDecimal amount;
    @Column(length = 3)
    private String counterPartyBankCode;
    private String counterPartyBankName;
    private String counterPartyBranch;
    private String counterAccountNumber;
    @Enumerated(EnumType.STRING)
    private AccountType counterAccountType;
    private String counterHolderName;
    @Enumerated(EnumType.STRING)
    private HolderType counterHolderType;
    private String counterHolderDocument;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id", nullable = false)
    private BankAccount bankAccountId;

    public Transaction(){

    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", bankAccountId=" + bankAccountId.getId() +
                ", counterPartyBankCode='" + counterPartyBankCode +
                ", counterPartyBankName='" + counterPartyBankName +
                ", counterPartyBranch='" + counterPartyBranch +
                ", counterAccountNumber='" + counterAccountNumber +
                ", counterAccountType=" + counterAccountType +
                ", counterHolderName='" + counterHolderName +
                ", counterHolderType=" + counterHolderType +
                ", counterHolderDocument='" + counterHolderDocument +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
