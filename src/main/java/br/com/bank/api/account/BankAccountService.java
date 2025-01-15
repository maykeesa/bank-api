package br.com.bank.api.account;

import br.com.bank.api.account.dto.BankAccountDto;
import br.com.bank.api.account.enums.AccountStatus;
import br.com.bank.api.account.utils.BankAccountServiceUtils;
import br.com.bank.api.balance.Balance;
import br.com.bank.api.balance.utils.BalanceServiceUtils;
import br.com.bank.api.utils.dto.ResponseDto;
import br.com.bank.api.utils.service.DtoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepo;

    @Autowired
    private BankAccountServiceUtils BankAccountServiceUtils;
    @Autowired
    private BalanceServiceUtils balanceServiceUils;

    public Page<BankAccountDto.Response.BankAccount> getAll(Pageable pageable){
        Page<BankAccount> accounts = this.bankAccountRepo.findAll(pageable);
        List<BankAccountDto.Response.BankAccount> accountsDto =
                DtoService.entitysToDtos(accounts.getContent(), BankAccountDto.Response.BankAccount.class);

        return new PageImpl<>(accountsDto, accounts.getPageable(), accounts.getTotalElements());
    }

    public Page<BankAccountDto.Response.BankAccount> getAllByBranch(String branch, Pageable pageable){
        Page<BankAccount> accounts = this.BankAccountServiceUtils.getAllByBranch(branch, pageable);
        List<BankAccountDto.Response.BankAccount> accountsDto =
                DtoService.entitysToDtos(accounts.getContent(), BankAccountDto.Response.BankAccount.class);

        return new PageImpl<>(accountsDto, accounts.getPageable(), accounts.getTotalElements());
    }

    public Page<BankAccountDto.Response.BankAccount> getAllByHolderDocument(String holderDocument, Pageable pageable) {
        Page<BankAccount> accounts = this.BankAccountServiceUtils.getAllByHolderDocument(holderDocument, pageable);
        List<BankAccountDto.Response.BankAccount> accountsDto =
                DtoService.entitysToDtos(accounts.getContent(), BankAccountDto.Response.BankAccount.class);

        return new PageImpl<>(accountsDto, accounts.getPageable(), accounts.getTotalElements());
    }

    public ResponseDto.Body.Response getByNumber(String number){
        BankAccount account = this.BankAccountServiceUtils.getByNumber(number);

        return DtoService.okResponseDto(account, BankAccountDto.Response.BankAccount.class);
    }

    public ResponseDto.Body.Response register(BankAccountDto.Request.Register dto) {
        this.BankAccountServiceUtils.verifyAccountHolder(dto.getHolderDocument(), dto.getType());

        BankAccount account = this.bankAccountRepo.saveAndFlush(this.BankAccountServiceUtils.getAccount(dto));
        Balance balance = this.balanceServiceUils.register(account);
        account.setBalance(balance);

        return DtoService.createdResponseDto(account, BankAccountDto.Response.BankAccount.class);
    }

    public ResponseDto.Body.Response update(UUID id, BankAccountDto.Request.Update dto){
        BankAccount bankAccount = this.bankAccountRepo.findById(id).map(account -> {
            if (Objects.nonNull(dto.getHolderEmail())) {
                account.setHolderEmail(dto.getHolderEmail());
            }

            if (Objects.nonNull(dto.getStatus())) {
                account.setStatus(AccountStatus.valueOf(dto.getStatus()));
            }

            return account;
        }).orElseThrow(() -> new EntityNotFoundException("The account id "+ id +" was not found."));
        bankAccount.setUpdateAt(LocalDateTime.now());

        this.bankAccountRepo.save(bankAccount);
        return DtoService.okResponseDto(bankAccount, BankAccountDto.Response.BankAccount.class);
    }

}
