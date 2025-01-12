package br.com.bank.api.transaction;

import br.com.bank.api.core.service.DtoService;
import br.com.bank.api.transaction.dto.TransactionDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionDto.Response.Transaction getOne(UUID id){
        Optional<Transaction> transactionOptional = this.transactionRepository.findById(id);

        if(transactionOptional.isEmpty()){
            throw new EntityNotFoundException("The transaction id "+ id +" was not found.");
        }

        return DtoService.entityToDto(transactionOptional.get(), TransactionDto.Response.Transaction.class);
    }


}
