package br.com.bank.api.balance.utils;

import br.com.bank.api.account.BankAccount;
import br.com.bank.api.balance.Balance;
import br.com.bank.api.balance.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceServiceUils {

    @Autowired
    private BalanceRepository balanceRepo;

    public void register(BankAccount account){
        Balance balance = new Balance(account, new BigDecimal(0), new BigDecimal(0));

        this.balanceRepo.saveAndFlush(balance);
    }
}
