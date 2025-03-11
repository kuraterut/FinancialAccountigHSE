package org.kuraterut.FinancialAccountingHSE.controllers;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.kuraterut.FinancialAccountingHSE.models.dto.CategoryDTO;
import org.kuraterut.FinancialAccountingHSE.services.CategoryService;
import org.kuraterut.FinancialAccountingHSE.utils.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping("/type")
    public ResponseEntity<List<CategoryDTO>> getCategoryByCategoryType(@RequestParam CategoryType categoryType) {
        List<CategoryDTO> categoryDTOs = categoryService.getCategoriesByCategoryType(categoryType);
        return ResponseEntity.ok(categoryDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategoryById(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        categoryDTO = categoryService.updateCategory(categoryDTO);
        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportCsv(@RequestParam String filepath) {
        categoryService.exportToCsv(filepath);
        return ResponseEntity.ok("CSV файл успешно экспортирован!");
    }

    @PostMapping("/import/csv")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        categoryService.importFromCsv(file);
        return ResponseEntity.ok("CSV файл успешно импортирован!");
    }

    @PostMapping("/import/xml")
    public ResponseEntity<String> importXml(@RequestParam("file") MultipartFile file) {
        categoryService.importFromXml(file);
        return ResponseEntity.ok("XML файл успешно импортирован!");
    }

    @GetMapping("/export/xml")
    public ResponseEntity<String> exportXml(@RequestParam String filepath) {
        categoryService.exportToXml(filepath);
        return ResponseEntity.ok("XML файл успешно экспортирован!");
    }
}
