package br.com.bank.api.balance;

import br.com.bank.api.balance.dto.BalanceDto;
import br.com.bank.api.balance.utils.BalanceServiceUils;
import br.com.bank.api.transaction.utils.TransactionServiceUtils;
import br.com.bank.api.utils.dto.ResponseDto;
import br.com.bank.api.utils.service.DtoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepo;

    @Autowired
    private BalanceServiceUils balanceServiceUils;
    @Autowired
    private TransactionServiceUtils transactionServiceUtils;

    public ResponseDto.Body.Response update(UUID id, BalanceDto.Request.Update dto) {
        Balance balanceAccount = this.balanceRepo.findById(id).map(balance -> {
            if (Objects.nonNull(dto.getBlockedAmount())) {
                balance.setBlockedAmount(dto.getBlockedAmount());
            }

            return balance;
        }).orElseThrow(() -> new EntityNotFoundException("The balance of the account with the id " + id + " was not found"));
        balanceAccount.getBankAccount().setUpdateAt(LocalDateTime.now());

        this.balanceRepo.save(balanceAccount);
        return DtoService.okResponseDto(balanceAccount, BalanceDto.Response.Balance.class);
    }
}
