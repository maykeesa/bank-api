package br.com.bank.api.transaction.utils;

import br.com.bank.api.account.BankAccount;
import br.com.bank.api.account.enums.AccountStatus;
import br.com.bank.api.account.enums.AccountType;
import br.com.bank.api.account.enums.HolderType;
import br.com.bank.api.balance.dto.BalanceDto;
import br.com.bank.api.config.exception.exceptions.AccountCounterPartyConflictException;
import br.com.bank.api.transaction.Transaction;
import br.com.bank.api.transaction.TransactionRepository;
import br.com.bank.api.transaction.dto.TransactionDto;
import br.com.bank.api.transaction.enums.TransactionType;
import br.com.bank.api.utils.service.DtoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceUtils {

    @Autowired
    private TransactionRepository transactionRepo;

    public Transaction getById(UUID id){
        Optional<Transaction> transactionOptional = this.transactionRepo.findById(id);

        if(transactionOptional.isEmpty()){
            throw new EntityNotFoundException("The transaction id "+ id +" was not found.");
        }

        return transactionOptional.get();
    }

    public void verifyDataCounterParty(
            TransactionDto.Request.Transaction dto, BankAccount bankAccount, BankAccount bankAccountCounterParty){

        if(!bankAccount.getStatus().equals(AccountStatus.ACTIVE) ||
                !bankAccountCounterParty.getStatus().equals(AccountStatus.ACTIVE)){
            throw new AccountCounterPartyConflictException("Some of the transaction accounts are not active");
        }

        if(!dto.getCounterPartyBranch().equals(bankAccountCounterParty.getBranch())){
            throw new AccountCounterPartyConflictException("The agency received does not match the account details of " +
                    "the counter-party");
        }

        if(!AccountType.valueOf(dto.getCounterAccountType()).equals(bankAccountCounterParty.getType())){
            throw new AccountCounterPartyConflictException("The type of account received does not match the counter party's " +
                    "account data");
        }

        if(!dto.getCounterHolderName().equals(bankAccountCounterParty.getHolderName())){
            throw new AccountCounterPartyConflictException("The name of the holder received does not match the data in the " +
                    "counter-party account");
        }

        if(!HolderType.valueOf(dto.getCounterHolderType()).equals(bankAccountCounterParty.getHolderType())){
            throw new AccountCounterPartyConflictException("The type of holder is not the same as the account details " +
                    "of the counter-party");
        }

        if(!dto.getCounterHolderDocument().equals(bankAccountCounterParty.getHolderDocument())){
            throw new AccountCounterPartyConflictException("The document provided does not match the account details " +
                    "of the counter-party");
        }
    }

    public Transaction getTransaction(
            TransactionDto.Request.Transaction dto, BankAccount bankAccount){

        Transaction transaction = DtoService.dtoToEntity(dto, Transaction.class);
        transaction.setId(null);
        transaction.setBankAccountId(bankAccount);

        return transaction;
    }

    public void sendTransaction(BankAccount bankAccount, BalanceDto.Request.Update dto){
        Transaction transaction = new Transaction();
        transaction.setBankAccountId(bankAccount);
        transaction.setAmount(dto.getBlockedAmount());


        if(bankAccount.getBalance().getBlockedAmount().compareTo(dto.getBlockedAmount()) > 0){
            transaction.setType(TransactionType.AMOUNT_RELEASE);
        }else{
            transaction.setType(TransactionType.AMOUNT_HOLD);
        }

        this.transactionRepo.save(transaction);
    }

    public String convertedDate(String date){
        if(Objects.nonNull(date)){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(date, dateFormatter).toString();
        }

        return null;
    }


}
