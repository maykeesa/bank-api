package br.com.bank.api.account;

import br.com.bank.api.account.dto.BankAccountDto;
import br.com.bank.api.account.enums.AccountStatus;
import br.com.bank.api.core.service.DtoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepo;

    public Page<BankAccountDto.Response.BankAccount> getAll(Pageable pageable){
        List<BankAccount> accounts = this.bankAccountRepo.findAll();
        List<BankAccountDto.Response.BankAccount> accountsDto =
                DtoService.entitysToDtos(accounts, BankAccountDto.Response.BankAccount.class);

        return new PageImpl<>(accountsDto, pageable, accountsDto.size());
    }

    public Page<BankAccountDto.Response.BankAccount> getAllByBranch(String branch, Pageable pageable){
        List<BankAccount> accounts = this.bankAccountRepo.findByBranch(branch);
        List<BankAccountDto.Response.BankAccount> accountsDto =
                DtoService.entitysToDtos(accounts, BankAccountDto.Response.BankAccount.class);

        return new PageImpl<>(accountsDto, pageable, accountsDto.size());
    }

    public Page<BankAccountDto.Response.BankAccount> getAllByHolderDocument(String holderDocument, Pageable pageable) {
        List<BankAccount> accounts = this.bankAccountRepo.findByHolderDocument(holderDocument);
        List<BankAccountDto.Response.BankAccount> accountsDto =
                DtoService.entitysToDtos(accounts, BankAccountDto.Response.BankAccount.class);

        return new PageImpl<>(accountsDto, pageable, accountsDto.size());
    }

    public BankAccountDto.Response.BankAccount getOneByNumber(String number){
        Optional<BankAccount> accountOptional = this.bankAccountRepo.findByNumber(number);

        if(accountOptional.isEmpty()){
            throw new EntityNotFoundException("The account number "+ number +" was not found.");
        }

        return DtoService.entityToDto(accountOptional.get(), BankAccountDto.Response.BankAccount.class);
    }

    public BankAccount post(BankAccountDto.Request.BankAccount dto) {
        return this.bankAccountRepo.save(this.register(dto));
    }

    private BankAccount register(BankAccountDto.Request.BankAccount dto){
        BankAccount account = DtoService.dtoToEntity(dto, BankAccount.class);
        account.setNumber(generateNumberAccount());
        account.setStatus(AccountStatus.valueOf(AccountStatus.ACTIVE.toString()));

        return account;
    }

    private String generateNumberAccount(){
        String randomNumber = String.valueOf(10000 + new Random().nextInt(900000000));

        if(this.bankAccountRepo.findByNumber(randomNumber).isPresent()){
            this.generateNumberAccount();
        }

        return randomNumber;
    }
}
