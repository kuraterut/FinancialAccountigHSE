package org.kuraterut.FinancialAccountingHSE.repositories;

import org.kuraterut.FinancialAccountingHSE.models.entities.Category;
import org.kuraterut.FinancialAccountingHSE.utils.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    Optional<List<Category>> findByCategoryType(CategoryType categoryType);
}
