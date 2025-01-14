package br.com.bank.api.account.utils;

import br.com.bank.api.account.BankAccount;
import br.com.bank.api.account.BankAccountRepository;
import br.com.bank.api.account.dto.BankAccountDto;
import br.com.bank.api.account.enums.AccountStatus;
import br.com.bank.api.config.exception.exceptions.AccountConflictException;
import br.com.bank.api.utils.service.DtoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class BankAccountServiceUtils {

    @Autowired
    private BankAccountRepository bankAccountRepo;

    public BankAccount getById(UUID id){
        Optional<BankAccount> accountOptional = this.bankAccountRepo.findById(id);

        if(accountOptional.isEmpty()){
            throw new EntityNotFoundException("The account with the id " + id + " was not found.");
        }

        return accountOptional.get();
    }

    public BankAccount getByNumber(String number){
        Optional<BankAccount> accountOptional = this.bankAccountRepo.findByNumber(number);

        if(accountOptional.isEmpty()){
            throw new EntityNotFoundException("The account number "+ number +" was not found.");
        }

        return accountOptional.get();
    }

    public List<BankAccount> getAllByBranch(String branch){
        List<BankAccount> accounts = this.bankAccountRepo.findByBranch(branch);

        if(accounts.isEmpty()){
            throw new EntityNotFoundException("Branch " + branch + " is not associated with any account.");
        }

        return accounts;
    }

    public List<BankAccount> getAllByHolderDocument(String holderDocument) {
        List<BankAccount> accounts = this.bankAccountRepo.findByHolderDocument(holderDocument);

        if (accounts.isEmpty()) {
            throw new EntityNotFoundException("Holder number " + holderDocument + " is not associated with any account.");
        }

        return accounts;
    }

    public void verifyAccountHolder(String holderDocument, String type){
        List<BankAccount> accounts = this.bankAccountRepo.findByHolderDocument(holderDocument);

        accounts.forEach(accoount -> {
            if(accoount.getType().toString().equals(type)){
                throw new AccountConflictException("There is already an account associated with the document " +
                        holderDocument + " of type " + type);
            }
        });
    }

    public BankAccount getAccount(BankAccountDto.Request.Register dto){
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
