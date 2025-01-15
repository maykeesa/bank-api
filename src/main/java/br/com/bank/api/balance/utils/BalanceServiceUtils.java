package br.com.bank.api.balance.utils;

import br.com.bank.api.account.BankAccount;
import br.com.bank.api.balance.Balance;
import br.com.bank.api.balance.BalanceRepository;
import br.com.bank.api.balance.dto.BalanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceServiceUtils {

    @Autowired
    private BalanceRepository balanceRepo;

    public Balance register(BankAccount account){
        Balance balance = new Balance(account, new BigDecimal(0), new BigDecimal(0));

        return this.balanceRepo.saveAndFlush(balance);
    }

}
