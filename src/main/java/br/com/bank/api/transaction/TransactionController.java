package br.com.bank.api.transaction;

import br.com.bank.api.transaction.dto.TransactionDto;
import br.com.bank.api.utils.dto.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto.Body.Response> getById(@PathVariable UUID id){

        return ResponseEntity.ok(this.transactionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDto.Body.Response> register(
            @Valid @RequestBody TransactionDto.Request.Transaction dto){
        ResponseDto.Body.Response response = this.transactionService.register(dto);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
