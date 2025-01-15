package br.com.bank.api.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    Page<BankAccount> findByBranch(String branch, Pageable pageable);

    List<BankAccount> findByHolderDocument(String holderDocument);

    Page<BankAccount> findByHolderDocument(String holderDocument, Pageable pageable);

    Optional<BankAccount> findByNumber(String number);

}
