package org.kuraterut.FinancialAccountingHSE.repositories;

import org.kuraterut.FinancialAccountingHSE.models.entities.Operation;
import org.kuraterut.FinancialAccountingHSE.utils.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByOperationType(OperationType operationType);
    List<Operation> findByBankAccountId(Long bankAccountId);

    // Поиск операций по конкретной дате
    @Query("SELECT o FROM Operation o WHERE o.dateTime = :dateTime")
    List<Operation> findByDateTime(@Param("dateTime") LocalDateTime dateTime);

    // Поиск операций после указанной даты (включая саму дату)
    @Query("SELECT o FROM Operation o WHERE o.dateTime >= :dateTime")
    List<Operation> findAfterDateTime(@Param("dateTime") LocalDateTime dateTime);

    // Поиск операций до указанной даты (включая саму дату)
    @Query("SELECT o FROM Operation o WHERE o.dateTime <= :dateTime")
    List<Operation> findBeforeDateTime(@Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT o FROM Operation o WHERE o.dateTime <= :end AND o.dateTime >= :start")
    List<Operation> findBetweenDateTimes(LocalDateTime start, LocalDateTime end);

    List<Operation> findByAmount(Double amount);

    @Query("SELECT o FROM Operation o WHERE o.amount <= :higher AND o.amount >= :lower")
    List<Operation> findByAmountBetween(@Param("lower") Double lower, @Param("higher") Double higher);

    @Query("SELECT o FROM Operation o WHERE o.amount >= :amount")
    List<Operation> findByAmountGreaterThan(@Param("amount") Double amount);

    @Query("SELECT o FROM Operation o WHERE o.amount <= :amount")
    List<Operation> findByAmountLessThan(@Param("amount") Double amount);

    List<Operation> findByCategoryId(Long categoryId);
}
