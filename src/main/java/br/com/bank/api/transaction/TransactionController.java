package br.com.bank.api.transaction;

import br.com.bank.api.transaction.dto.TransactionDto;
import br.com.bank.api.utils.dto.ResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<TransactionDto.Response.Transaction>> getAll(
            @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$", message = "The date received does " +
                    "not follow the standard format: dd-mm-yyyy")
            @RequestParam(required = false) String date,
            @Pattern(regexp = "CREDIT|DEBIT|AMOUNT_HOLD|AMOUNT_RELEASE", message = "The value of the type field can " +
                    "only be CREDIT, DEBIT, AMOUNT_HOLD or AMOUNT_RELEASE")
            @RequestParam(required = false) String type,
            @PageableDefault(sort = "created_at", size = 15, direction = Sort.Direction.DESC) Pageable pageable){

        return ResponseEntity.ok(this.transactionService.getAll(date, type, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto.Body.Response> getById(@PathVariable UUID id){
        return ResponseEntity.ok(this.transactionService.getById(id));
    }

    @GetMapping("/bank-code/{bankCode}/number-counter-party/{numberCounterParty}")
    public ResponseEntity<Page<TransactionDto.Response.Transaction>> getAllByBankCodeAndNumberCounterParty(
            @PathVariable String bankCode,
            @PathVariable String numberCounterParty,
            @PageableDefault(sort = "createdAt", size = 15, direction = Sort.Direction.DESC) Pageable pageable){

        return ResponseEntity.ok(this.transactionService
                .getAllByBankCodeAndNumberCounterParty(bankCode, numberCounterParty, pageable));
    }

    @PostMapping
    public ResponseEntity<ResponseDto.Body.Response> register(
            @Valid @RequestBody TransactionDto.Request.Transaction dto){
        ResponseDto.Body.Response response = this.transactionService.register(dto);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
