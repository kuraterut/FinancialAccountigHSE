package org.kuraterut.FinancialAccountingHSE.controllers;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.kuraterut.FinancialAccountingHSE.models.dto.BankAccountDTO;
import org.kuraterut.FinancialAccountingHSE.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController("bankAccountController")
@RequestMapping("/api/bank-account")
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping
    public ResponseEntity<BankAccountDTO> createBankAccount(@RequestBody BankAccountDTO bankAccountDTO) {
        BankAccountDTO createdBankAccount = bankAccountService.createBankAccount(bankAccountDTO);
        return ResponseEntity.ok(createdBankAccount);
    }

    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccounts() {
        List<BankAccountDTO> bankAccounts = bankAccountService.getAllBankAccounts();
        return ResponseEntity.ok(bankAccounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDTO> getBankAccountById(@PathVariable Long id) {
        BankAccountDTO bankAccountDTO = bankAccountService.getBankAccountById(id);
        return ResponseEntity.ok(bankAccountDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Long id) {
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDTO> updateBankAccount(@PathVariable Long id, @RequestBody BankAccountDTO bankAccountDTO) {
        bankAccountDTO.setId(id);
        bankAccountDTO = bankAccountService.updateBankAccount(bankAccountDTO);
        return ResponseEntity.ok(bankAccountDTO);
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportCsv(@RequestParam String filepath) {
        bankAccountService.exportToCsv(filepath);
        return ResponseEntity.ok("CSV файл успешно экспортирован!");
    }

    @PostMapping("/import/csv")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        bankAccountService.importFromCsv(file);
        return ResponseEntity.ok("CSV файл успешно импортирован!");
    }

    @PostMapping("/import/xml")
    public ResponseEntity<String> importXml(@RequestParam("file") MultipartFile file) {
        bankAccountService.importFromXml(file);
        return ResponseEntity.ok("XML файл успешно импортирован!");
    }

    @GetMapping("/export/xml")
    public ResponseEntity<String> exportXml(@RequestParam String filepath) {
        bankAccountService.exportToXml(filepath);
        return ResponseEntity.ok("XML файл успешно экспортирован!");
    }

}
