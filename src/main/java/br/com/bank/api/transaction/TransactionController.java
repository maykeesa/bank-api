package br.com.bank.api.transaction;

import br.com.bank.api.transaction.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto.Response.Transaction> getOne(@PathVariable UUID id){
        return ResponseEntity.ok(this.transactionService.getOne(id));
    }

    //O sistema deve permitir buscar transações filtrando pelo código do banco e número da conta bancária da contraparte.

}
