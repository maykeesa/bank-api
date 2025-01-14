package br.com.bank.api.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    List<BankAccount> findByBranch(String branch);

    List<BankAccount> findByHolderDocument(String holderDocument);

    Optional<BankAccount> findByNumber(String number);

}
