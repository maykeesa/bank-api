package br.com.bank.api.balance;

import br.com.bank.api.account.BankAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "balances", schema = "bank")
public class Balance {

    @Id
    @OneToOne
    private BankAccount bankAccountId;
    @Column(length = 18, precision = 2)
    private BigDecimal availableAmount;
    @Column(length = 18, precision = 2)
    private BigDecimal blockedAmount;

    public Balance(){

    }

}
