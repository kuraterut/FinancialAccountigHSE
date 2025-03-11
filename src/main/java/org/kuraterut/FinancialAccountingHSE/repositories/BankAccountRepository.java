package org.kuraterut.FinancialAccountingHSE.repositories;

import org.kuraterut.FinancialAccountingHSE.models.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByName(String name);

    void deleteByName(String name);
//    void deleteById(Long id);
}
