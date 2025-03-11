package org.kuraterut.FinancialAccountingHSE.services;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.transaction.Transactional;
import org.kuraterut.FinancialAccountingHSE.adapters.CsvAdapter;
import org.kuraterut.FinancialAccountingHSE.adapters.XmlAdapter;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.bankAccount.BankAccountAlreadyExistsException;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.bankAccount.BankAccountNotFoundException;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.fileLoader.ExportException;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.fileLoader.ImportException;
import org.kuraterut.FinancialAccountingHSE.models.dto.BankAccountDTO;
import org.kuraterut.FinancialAccountingHSE.models.dto.OperationDTO;
import org.kuraterut.FinancialAccountingHSE.models.entities.BankAccount;
import org.kuraterut.FinancialAccountingHSE.models.entities.Operation;
import org.kuraterut.FinancialAccountingHSE.repositories.BankAccountRepository;
import org.kuraterut.FinancialAccountingHSE.services.mappers.BankAccountMapper;
import org.kuraterut.FinancialAccountingHSE.services.mappers.OperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;


    public BankAccountDTO createBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccountCheck = bankAccountRepository.findByName(bankAccountDTO.getName())
                .orElse(null);
        if(bankAccountCheck != null) {
            throw new BankAccountAlreadyExistsException("Bank with name '" + bankAccountDTO.getName() + "' already exists");
        }
        BankAccount bankAccount = BankAccountMapper.toEntity(bankAccountDTO);
        bankAccount = bankAccountRepository.save(bankAccount);
        return BankAccountMapper.toDTO(bankAccount) ;
    }

    public List<BankAccountDTO> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOs = new ArrayList<>();
        for (BankAccount bankAccount : bankAccounts) {
            bankAccountDTOs.add(BankAccountMapper.toDTO(bankAccount));
        }
        return bankAccountDTOs;
    }

    public BankAccountDTO getBankAccountById(Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found by ID " + bankAccountId.toString()));

        return BankAccountMapper.toDTO(bankAccount);
    }

    public BankAccountDTO updateBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountDTO.getId())
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found by ID " + bankAccountDTO.getId()));
        bankAccountRepository.save(BankAccountMapper.toEntity(bankAccountDTO));
        return bankAccountDTO;
    }

    public void deleteBankAccount(Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found by ID " + bankAccountId.toString()));
        bankAccountRepository.deleteById(bankAccountId);
    }
    @Transactional
    public void exportToCsv(String filePath){
        try{
            List<BankAccount> bankAccounts = bankAccountRepository.findAll();
            List<BankAccountDTO> bankAccountDTOs = new ArrayList<>();
            for (BankAccount bankAccount : bankAccounts) {
                bankAccountDTOs.add(BankAccountMapper.toDTO(bankAccount));
            }
            CsvAdapter.writeCsv(filePath, bankAccountDTOs, BankAccountDTO.class);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new ExportException("Error with export operations to CSV file: " + e.getMessage());
        }
    }
    @Transactional
    public void importFromCsv(MultipartFile file) {
        try {
            List<BankAccountDTO> bankAccountDTOs = CsvAdapter.readCsv(file.getInputStream(), BankAccountDTO.class);
            List<BankAccount> bankAccounts = new ArrayList<>();
            for (BankAccountDTO bankAccountDTO : bankAccountDTOs) {
                bankAccounts.add(BankAccountMapper.toEntity(bankAccountDTO));
            }
            bankAccountRepository.saveAll(bankAccounts);
        } catch (IOException e) {
            throw new ImportException("Error with import operations from CSV file: " + e.getMessage());
        }
    }

    @Transactional
    public void importFromXml(MultipartFile file){
        try {
            List<BankAccountDTO> bankAccountDTOs = XmlAdapter.readXmlList(file.getInputStream(), BankAccountDTO.class);
            List<BankAccount> bankAccounts = new ArrayList<>();
            for (BankAccountDTO bankAccountDTO : bankAccountDTOs) {
                bankAccounts.add(BankAccountMapper.toEntity(bankAccountDTO));
            }
            bankAccountRepository.saveAll(bankAccounts);
        } catch (IOException e) {
            throw new ImportException("Error with import operations from XML file: " + e.getMessage());
        }
    }
    @Transactional
    public void exportToXml(String filePath) {
        try{
            List<BankAccount> bankAccounts = bankAccountRepository.findAll();
            List<BankAccountDTO> bankAccountDTOs = new ArrayList<>();
            for (BankAccount bankAccount : bankAccounts) {
                bankAccountDTOs.add(BankAccountMapper.toDTO(bankAccount));
            }
            XmlAdapter.writeXmlList(filePath, bankAccountDTOs);
        } catch (IOException e) {
            throw new ExportException("Error with export operations to XML file: " + e.getMessage());
        }
    }

}
