package br.com.bank.api.transaction;

import br.com.bank.api.transaction.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = """ 
           SELECT * FROM transactions.transactions t
           WHERE
                (:createdAt IS NULL OR CAST(DATE(t.created_at) AS TEXT) = :createdAt)
                AND (:type IS NULL OR t.type = :type)
           ORDER BY t.created_at DESC
           """, nativeQuery = true)
    Page<Transaction> findAll(String createdAt, String type, Pageable pageable);

    @Query("""
            SELECT t FROM Transaction t
            WHERE
                t.counterPartyBankCode = :counterPartyBankCode
                AND t.counterAccountNumber = :counterAccountNumber
            """)
    Page<Transaction> findAllByBankCodeAndNumberCounterParty(
            String counterPartyBankCode, String counterAccountNumber, Pageable pageable);
}
