package org.kuraterut.FinancialAccountingHSE.controllers;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.kuraterut.FinancialAccountingHSE.models.dto.OperationDTO;
import org.kuraterut.FinancialAccountingHSE.services.OperationService;
import org.kuraterut.FinancialAccountingHSE.utils.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/operation")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping
    public ResponseEntity<OperationDTO> createOperation(@RequestBody OperationDTO operationDTO) {
        OperationDTO operation = operationService.createOperation(operationDTO);
        return ResponseEntity.ok(operation);
    }

    @GetMapping
    public ResponseEntity<List<OperationDTO>> getAllOperations() {
        List<OperationDTO> operations = operationService.getAllOperations();
        return ResponseEntity.ok(operations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDTO> getOperationById(@PathVariable Long id) {
        OperationDTO operation = operationService.getOperationById(id);
        return ResponseEntity.ok(operation);
    }

    @GetMapping("/bank-account")
    public ResponseEntity<List<OperationDTO>> getOperationsByBankAccountId(@RequestParam Long bankAccountId) {
        List<OperationDTO> operationDTOs = operationService.getOperationsByBankAccountId(bankAccountId);
        return ResponseEntity.ok(operationDTOs);
    }

    @GetMapping("/category")
    public ResponseEntity<List<OperationDTO>> getOperationsByCategoryId(@RequestParam Long categoryId) {
        List<OperationDTO> operationDTOs = operationService.getOperationsByCategoryId(categoryId);
        return ResponseEntity.ok(operationDTOs);
    }

    @GetMapping("/type")
    public ResponseEntity<List<OperationDTO>> getOperationsByOperationType(@RequestParam OperationType operationType) {
        List<OperationDTO> operationDTOs = operationService.getOperationsByOperationType(operationType);
        return ResponseEntity.ok(operationDTOs);
    }

    @GetMapping("/date-time/equal")
    public ResponseEntity<List<OperationDTO>> getOperationsByDateTime(@RequestParam LocalDateTime dateTime) {
        List<OperationDTO> operationDTOs = operationService.getOperationsByDateTime(dateTime);
        return ResponseEntity.ok(operationDTOs);
    }

    @GetMapping("/date-time/before")
    public ResponseEntity<List<OperationDTO>> getOperationsBeforeDateTime(@RequestParam LocalDateTime dateTime) {
        List<OperationDTO> operationDTOs = operationService.getOperationsBeforeDateTime(dateTime);
        return ResponseEntity.ok(operationDTOs);
    }

    @GetMapping("/date-time/after")
    public ResponseEntity<List<OperationDTO>> getOperationsAfterDateTime(@RequestParam LocalDateTime dateTime) {
        List<OperationDTO> operationDTOs = operationService.getOperationsAfterDateTime(dateTime);
        return ResponseEntity.ok(operationDTOs);
    }

    @GetMapping("date-time/between")
    public ResponseEntity<List<OperationDTO>> getOperationsBetweenDateTime(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        List<OperationDTO> operationDTOs = operationService.getOperationsBetweenDateTimes(start, end);
        return ResponseEntity.ok(operationDTOs);
    }

    @GetMapping("/amount/equal")
    public ResponseEntity<List<OperationDTO>> getOperationsByAmount(@RequestParam Double amount) {
        List<OperationDTO> operationDTOs = operationService.getOperationsByAmount(amount);
        return ResponseEntity.ok(operationDTOs);
    }

    @GetMapping("/amount/greater")
    public ResponseEntity<List<OperationDTO>> getOperationsByAmountGreaterThan(@RequestParam Double amount) {
        List<OperationDTO> operationDTOs = operationService.getOperationsByAmountGreaterThan(amount);
        return ResponseEntity.ok(operationDTOs);
    }

    @GetMapping("/amount/less")
    public ResponseEntity<List<OperationDTO>> getOperationsByAmountLessThan(@RequestParam Double amount) {
        List<OperationDTO> operationDTOs = operationService.getOperationsByAmountLessThan(amount);
        return ResponseEntity.ok(operationDTOs);
    }

    @GetMapping("/amount/between")
    public ResponseEntity<List<OperationDTO>> getOperationsByAmountBetween(@RequestParam Double start, @RequestParam Double end) {
        List<OperationDTO> operationDTOs = operationService.getOperationsByAmountBetween(start, end);
        return ResponseEntity.ok(operationDTOs);
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportCsv(@RequestParam String filepath) {
        operationService.exportToCsv(filepath);
        return ResponseEntity.ok("CSV файл успешно экспортирован!");
    }

    @PostMapping("/import/csv")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        operationService.importFromCsv(file);
        return ResponseEntity.ok("CSV файл успешно импортирован!");
    }

    @PostMapping("/import/xml")
    public ResponseEntity<String> importXml(@RequestParam("file") MultipartFile file) {
        operationService.importFromXml(file);
        return ResponseEntity.ok("XML файл успешно импортирован!");
    }

    @GetMapping("/export/xml")
    public ResponseEntity<String> exportXml(@RequestParam String filepath) {
        operationService.exportToXml(filepath);
        return ResponseEntity.ok("XML файл успешно экспортирован!");
    }
}
