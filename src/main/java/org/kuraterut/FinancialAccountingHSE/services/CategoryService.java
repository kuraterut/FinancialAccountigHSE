package org.kuraterut.FinancialAccountingHSE.services;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.kuraterut.FinancialAccountingHSE.adapters.CsvAdapter;
import org.kuraterut.FinancialAccountingHSE.adapters.XmlAdapter;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.category.CategoryAlreadyExistsException;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.category.CategoryNotFoundException;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.fileLoader.ExportException;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.fileLoader.ImportException;
import org.kuraterut.FinancialAccountingHSE.models.dto.CategoryDTO;
import org.kuraterut.FinancialAccountingHSE.models.dto.OperationDTO;
import org.kuraterut.FinancialAccountingHSE.models.entities.BankAccount;
import org.kuraterut.FinancialAccountingHSE.models.entities.Category;
import org.kuraterut.FinancialAccountingHSE.models.entities.Operation;
import org.kuraterut.FinancialAccountingHSE.repositories.CategoryRepository;
import org.kuraterut.FinancialAccountingHSE.services.mappers.CategoryMapper;
import org.kuraterut.FinancialAccountingHSE.services.mappers.OperationMapper;
import org.kuraterut.FinancialAccountingHSE.utils.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category categoryCheck = categoryRepository.findByName(categoryDTO.getName())
                .orElse(null);
        if(categoryCheck != null) {
            throw new CategoryAlreadyExistsException("Category with name '" + categoryDTO.getName() + "' already exists");
        }
        Category category = CategoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return CategoryMapper.toDTO(category);
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (Category category : categories) {
            categoryDTOs.add(CategoryMapper.toDTO(category));
        }
        return categoryDTOs;
    }

    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found by ID " + categoryId));
        return CategoryMapper.toDTO(category);
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found by ID " + categoryDTO.getId()));
        categoryRepository.save(CategoryMapper.toEntity(categoryDTO));
        return categoryDTO;
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found by ID " + categoryId));
        categoryRepository.deleteById(categoryId);
    }


    public List<CategoryDTO> getCategoriesByCategoryType(CategoryType type) {
        List<Category> categories = categoryRepository.findByCategoryType(type)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found by type " + type));
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (Category category : categories) {
            categoryDTOs.add(CategoryMapper.toDTO(category));
        }
        return categoryDTOs;
    }

    public void exportToCsv(String filePath){
        try{
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDTO> categoryDTOs = new ArrayList<>();
            for (Category category : categories) {
                categoryDTOs.add(CategoryMapper.toDTO(category));
            }
            CsvAdapter.writeCsv(filePath, categoryDTOs, CategoryDTO.class);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new ExportException("Error with export categories to CSV file: " + e.getMessage());
        }
    }

    public void importFromCsv(MultipartFile file) {
        try {
            List<CategoryDTO> categoryDTOs = CsvAdapter.readCsv(file.getInputStream(), CategoryDTO.class);
            List<Category> categories = new ArrayList<>();
            for (CategoryDTO categoryDTO : categoryDTOs) {
                categories.add(CategoryMapper.toEntity(categoryDTO));
            }
            categoryRepository.saveAll(categories);
        } catch (IOException e) {
            throw new ImportException("Error with import categories from CSV file: " + e.getMessage());
        }
    }

    public void importFromXml(MultipartFile file){
        try {
            List<CategoryDTO> categoryDTOs = XmlAdapter.readXmlList(file.getInputStream(), CategoryDTO.class);
            List<Category> categories = new ArrayList<>();
            for (CategoryDTO categoryDTO : categoryDTOs) {
                categories.add(CategoryMapper.toEntity(categoryDTO));
            }
            categoryRepository.saveAll(categories);
        } catch (IOException e) {
            throw new ImportException("Error with import categories from XML file: " + e.getMessage());
        }
    }

    public void exportToXml(String filePath) {
        try{
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDTO> categoryDTOs = new ArrayList<>();
            for (Category category : categories) {
                categoryDTOs.add(CategoryMapper.toDTO(category));
            }
            XmlAdapter.writeXmlList(filePath, categoryDTOs);
        } catch (IOException e) {
            throw new ExportException("Error with export categories to XML file: " + e.getMessage());
        }
    }
}
