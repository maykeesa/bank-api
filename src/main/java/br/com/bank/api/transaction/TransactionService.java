package br.com.bank.api.transaction;

import br.com.bank.api.account.BankAccount;
import br.com.bank.api.account.utils.BankAccountServiceUtils;
import br.com.bank.api.transaction.dto.TransactionDto;
import br.com.bank.api.transaction.enums.TransactionType;
import br.com.bank.api.transaction.utils.TransactionServiceUtils;
import br.com.bank.api.utils.dto.ResponseDto;
import br.com.bank.api.utils.service.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionServiceUtils transactionServiceUtils;
    @Autowired
    private BankAccountServiceUtils BankAccountServiceUtils;

    public Page<TransactionDto.Response.Transaction> getAll(String date, String type, Pageable pageable) {
        String convertedDate = this.transactionServiceUtils.convertedDate(date);

        Page<Transaction> transactions = this.transactionRepository.findAll(convertedDate, type, pageable);
        List<TransactionDto.Response.Transaction> transactionsDto =
                DtoService.entitysToDtos(transactions.getContent(), TransactionDto.Response.Transaction.class);

        return new PageImpl<>(transactionsDto, transactions.getPageable(), transactions.getTotalElements());
    }

    public ResponseDto.Body.Response getById(UUID id){
        Transaction transaction = this.transactionServiceUtils.getById(id);

        return DtoService.okResponseDto(transaction, TransactionDto.Response.Transaction.class);
    }

    public Page<TransactionDto.Response.Transaction> getAllByBankCodeAndNumberCounterParty(
            String bankCode, String numberCounterParty, Pageable pageable) {
        Page<Transaction> transactions = this.transactionRepository
                .findAllByBankCodeAndNumberCounterParty(bankCode, numberCounterParty, pageable);
        List<TransactionDto.Response.Transaction> transactionsDto =
                DtoService.entitysToDtos(transactions.getContent(), TransactionDto.Response.Transaction.class);

        return new PageImpl<>(transactionsDto, transactions.getPageable(), transactions.getTotalElements());
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
