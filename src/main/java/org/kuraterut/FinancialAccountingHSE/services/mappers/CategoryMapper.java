package org.kuraterut.FinancialAccountingHSE.services.mappers;

import org.kuraterut.FinancialAccountingHSE.models.dto.CategoryDTO;
import org.kuraterut.FinancialAccountingHSE.models.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setCategoryType(category.getCategoryType());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }
    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setCategoryType(categoryDTO.getCategoryType());
        category.setName(categoryDTO.getName());
        return category;
    }
}
