package org.kuraterut.FinancialAccountingHSE.services;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.kuraterut.FinancialAccountingHSE.adapters.CsvAdapter;
import org.kuraterut.FinancialAccountingHSE.adapters.XmlAdapter;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.fileLoader.ExportException;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.fileLoader.ImportException;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.operation.OperationNotFoundException;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.operation.OperationValidationException;
import org.kuraterut.FinancialAccountingHSE.models.dto.BankAccountDTO;
import org.kuraterut.FinancialAccountingHSE.models.dto.CategoryDTO;
import org.kuraterut.FinancialAccountingHSE.models.dto.OperationDTO;
import org.kuraterut.FinancialAccountingHSE.models.entities.BankAccount;
import org.kuraterut.FinancialAccountingHSE.models.entities.Category;
import org.kuraterut.FinancialAccountingHSE.models.entities.Operation;
import org.kuraterut.FinancialAccountingHSE.repositories.BankAccountRepository;
import org.kuraterut.FinancialAccountingHSE.repositories.OperationRepository;
import org.kuraterut.FinancialAccountingHSE.services.mappers.BankAccountMapper;
import org.kuraterut.FinancialAccountingHSE.services.mappers.CategoryMapper;
import org.kuraterut.FinancialAccountingHSE.services.mappers.OperationMapper;
import org.kuraterut.FinancialAccountingHSE.utils.OperationType;
import org.kuraterut.FinancialAccountingHSE.validation.Validation;
import org.kuraterut.FinancialAccountingHSE.validation.ValidationResponse;
import org.kuraterut.FinancialAccountingHSE.validation.operation.OperationAmountValidation;
import org.kuraterut.FinancialAccountingHSE.validation.operation.OperationBankAccountBalanceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OperationService {
    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CategoryService categoryService;

    public OperationDTO createOperation(OperationDTO operationDTO) {
        BankAccountDTO bankAccountDTO = bankAccountService.getBankAccountById(operationDTO.getBankAccountId());
        CategoryDTO categoryDTO = categoryService.getCategoryById(operationDTO.getCategoryId());

        Operation operation = OperationMapper.toEntity(operationDTO);
        operation.setBankAccount(BankAccountMapper.toEntity(bankAccountDTO));
        operation.setCategory(CategoryMapper.toEntity(categoryDTO));

        List<Validation> validations = new ArrayList<>();
        validations.add(new OperationAmountValidation(operation));
        validations.add(new OperationBankAccountBalanceValidation(operation));
        for (Validation validation : validations) {
            ValidationResponse validationResponse = validation.validate();
            if(validationResponse.getErrorCode() != 200){
                throw new OperationValidationException(validationResponse.getErrorMessage());
            }
        }

        if(operation.getOperationType() == OperationType.EXPENSE){
            bankAccountDTO.setBalance(bankAccountDTO.getBalance() - operation.getAmount());
        }
        else if (operation.getOperationType() == OperationType.INCOME){
            bankAccountDTO.setBalance(bankAccountDTO.getBalance() + operation.getAmount());
        }

        bankAccountService.updateBankAccount(bankAccountDTO);
        operationRepository.save(operation);
        return OperationMapper.toDTO(operation);
    }


    public List<OperationDTO> getAllOperations() {
        List<Operation> operations = operationRepository.findAll();
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }

    public OperationDTO getOperationById(Long operationId) {
        Operation operation = operationRepository.findById(operationId)
                .orElseThrow(() -> new OperationNotFoundException("Operation not found by ID " + operationId));
        return OperationMapper.toDTO(operation);
    }

    public List<OperationDTO> getOperationsByBankAccountId(Long bankAccountId) {
        List<Operation> operations = operationRepository.findByBankAccountId(bankAccountId);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }

    public List<OperationDTO> getOperationsByCategoryId(Long categoryId) {
        List<Operation> operations = operationRepository.findByCategoryId(categoryId);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }
    public List<OperationDTO> getOperationsByOperationType(OperationType operationType) {
        List<Operation> operations = operationRepository.findByOperationType(operationType);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }
    public List<OperationDTO> getOperationsByDateTime(LocalDateTime dateTime) {
        List<Operation> operations = operationRepository.findByDateTime(dateTime);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }

    public List<OperationDTO> getOperationsBeforeDateTime(LocalDateTime dateTime) {
        List<Operation> operations = operationRepository.findBeforeDateTime(dateTime);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }

    public List<OperationDTO> getOperationsAfterDateTime(LocalDateTime dateTime) {
        List<Operation> operations = operationRepository.findAfterDateTime(dateTime);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }

    public List<OperationDTO> getOperationsBetweenDateTimes(LocalDateTime start, LocalDateTime end) {
        List<Operation> operations = operationRepository.findBetweenDateTimes(start, end);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }

    public List<OperationDTO> getOperationsByAmount(Double amount) {
        List<Operation> operations = operationRepository.findByAmount(amount);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }

    public List<OperationDTO> getOperationsByAmountGreaterThan(Double amount) {
        List<Operation> operations = operationRepository.findByAmountGreaterThan(amount);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }

    public List<OperationDTO> getOperationsByAmountLessThan(Double amount) {
        List<Operation> operations = operationRepository.findByAmountLessThan(amount);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }
    public List<OperationDTO> getOperationsByAmountBetween(Double start, Double end) {
        List<Operation> operations = operationRepository.findByAmountBetween(start, end);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(OperationMapper.toDTO(operation));
        }
        return operationDTOs;
    }

    public void exportToCsv(String filePath){
        try{
            List<Operation> operations = operationRepository.findAll();
            List<OperationDTO> operationDTOs = new ArrayList<>();
            for (Operation operation : operations) {
                operationDTOs.add(OperationMapper.toDTO(operation));
            }
            CsvAdapter.writeCsv(filePath, operationDTOs, OperationDTO.class);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new ExportException("Error with export operations to CSV file: " + e.getMessage());
        }
    }

    public void importFromCsv(MultipartFile file) {
        try {
            List<OperationDTO> operationDTOs = CsvAdapter.readCsv(file.getInputStream(), OperationDTO.class);
            List<Operation> operations = new ArrayList<>();
            for (OperationDTO operationDTO : operationDTOs) {
                operations.add(OperationMapper.toEntity(operationDTO));
            }
            operationRepository.saveAll(operations);
        } catch (IOException e) {
            throw new ImportException("Error with import operations from CSV file: " + e.getMessage());
        }
    }

    public void importFromXml(MultipartFile file){
        try {
            List<OperationDTO> operationDTOs = XmlAdapter.readXmlList(file.getInputStream(), OperationDTO.class);
            List<Operation> operations = new ArrayList<>();
            for (OperationDTO operationDTO : operationDTOs) {
                operations.add(OperationMapper.toEntity(operationDTO));
            }
            operationRepository.saveAll(operations);
        } catch (IOException e) {
            throw new ImportException("Error with import operations from XML file: " + e.getMessage());
        }
    }

    public void exportToXml(String filePath) {
        try{
            List<Operation> operations = operationRepository.findAll();
            List<OperationDTO> operationDTOs = new ArrayList<>();
            for (Operation operation : operations) {
                operationDTOs.add(OperationMapper.toDTO(operation));
            }
            XmlAdapter.writeXmlList(filePath, operationDTOs);
        } catch (IOException e) {
            throw new ExportException("Error with export operations to XML file: " + e.getMessage());
        }
    }
}
