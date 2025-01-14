package br.com.bank.api.balance;

import br.com.bank.api.balance.dto.BalanceDto;
import br.com.bank.api.utils.dto.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto.Body.Response> update(
            @PathVariable UUID id,
            @Valid @RequestBody BalanceDto.Request.Update dto){

        return ResponseEntity.ok(this.balanceService.update(id, dto));
    }
}
