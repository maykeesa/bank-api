package br.com.bank.api.transaction;

import br.com.bank.api.account.BankAccount;
import br.com.bank.api.account.utils.BankAccountServiceUtils;
import br.com.bank.api.transaction.dto.TransactionDto;
import br.com.bank.api.transaction.utils.TransactionServiceUtils;
import br.com.bank.api.utils.dto.ResponseDto;
import br.com.bank.api.utils.service.DtoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionServiceUtils transactionServiceUtils;
    @Autowired
    private BankAccountServiceUtils BankAccountServiceUtils;

    public ResponseDto.Body.Response getById(UUID id){
        Optional<Transaction> transactionOptional = this.transactionRepository.findById(id);

        if(transactionOptional.isEmpty()){
            throw new EntityNotFoundException("The transaction id "+ id +" was not found.");
        }

        return DtoService.okResponseDto(transactionOptional.get(), TransactionDto.Response.Transaction.class);
    }

    public ResponseDto.Body.Response register(TransactionDto.Request.Transaction dto){
        BankAccount bankAccount = this.BankAccountServiceUtils.getById(UUID.fromString(dto.getBankAccountId()));
        BankAccount bankAccountCounterParty = this.BankAccountServiceUtils.getByNumber(dto.getCounterAccountNumber());

        this.transactionServiceUtils.verifyDataCounterParty(dto, bankAccount, bankAccountCounterParty);
        Transaction transaction =
                this.transactionRepository.save(this.transactionServiceUtils.getTransaction(dto, bankAccount));

        return DtoService.createdResponseDto(transaction, TransactionDto.Response.Transaction.class);
    }

}
