package br.com.bank.api.account;

import br.com.bank.api.account.dto.BankAccountDto;
import br.com.bank.api.utils.dto.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/bank-account")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity<Page<BankAccountDto.Response.BankAccount>> getAll(
            @PageableDefault(sort = "holderName", size = 15, direction = Sort.Direction.ASC) Pageable pageable){

        return ResponseEntity.ok(this.bankAccountService.getAll(pageable));
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<ResponseDto.Body.Response> getByNumber(@PathVariable String number){

        return ResponseEntity.ok(this.bankAccountService.getByNumber(number));
    }

    @GetMapping("/branch/{branch}")
    public ResponseEntity<Page<BankAccountDto.Response.BankAccount>> getAllByBranch(
            @PageableDefault(sort = "holderName", size = 15, direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable String branch){

        return ResponseEntity.ok(this.bankAccountService.getAllByBranch(branch, pageable));
    }

    @GetMapping("/holder-document/{holderDocument}")
    public ResponseEntity<Page<BankAccountDto.Response.BankAccount>> getAllByHolderDocument(
            @PageableDefault(sort = "holderName", size = 15, direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable String holderDocument){

        return ResponseEntity.ok(this.bankAccountService.getAllByHolderDocument(holderDocument, pageable));
    }

    @PostMapping
    public ResponseEntity<ResponseDto.Body.Response> register(@Valid @RequestBody BankAccountDto.Request.Register dto){
        ResponseDto.Body.Response response = this.bankAccountService.register(dto);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto.Body.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody BankAccountDto.Request.Update dto){

        return ResponseEntity.ok(this.bankAccountService.update(id, dto));
    }

}
