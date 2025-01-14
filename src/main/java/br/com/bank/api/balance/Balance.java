package br.com.bank.api.balance;

import br.com.bank.api.account.BankAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "balances", schema = "bank")
@AllArgsConstructor
public class Balance {

    @Id
    private UUID bankAccountId;
    @Column(precision = 16, scale = 2)
    private BigDecimal availableAmount;
    @Column(precision = 16, scale = 2)
    private BigDecimal blockedAmount;

    @MapsId
    @OneToOne
    @JoinColumn(name = "bankAccountId", referencedColumnName = "id")
    private BankAccount bankAccount;

    public Balance(){

    }

    public Balance(BankAccount account, BigDecimal availableAmount, BigDecimal blockedAmount){
        this.bankAccount = account;
        this.availableAmount = availableAmount;
        this.blockedAmount = blockedAmount;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "bankAccountId=" + bankAccountId +
                ", availableAmount=" + availableAmount +
                ", blockedAmount=" + blockedAmount +
                '}';
    }
}
