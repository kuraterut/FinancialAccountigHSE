package org.kuraterut.FinancialAccountingHSE.services.mappers;

import org.kuraterut.FinancialAccountingHSE.models.dto.OperationDTO;
import org.kuraterut.FinancialAccountingHSE.models.entities.Operation;
import org.springframework.stereotype.Component;

@Component
public class OperationMapper {
    public static OperationDTO toDTO(Operation operation) {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setId(operation.getId());
        operationDTO.setOperationType(operation.getOperationType());
        operationDTO.setBankAccountId(operation.getBankAccount().getId());
        operationDTO.setAmount(operation.getAmount());
        operationDTO.setDateTime(operation.getDateTime());
        operationDTO.setDescription(operation.getDescription());
        operationDTO.setCategoryId(operation.getCategory().getId());
        return operationDTO;
    }

    //BankAccount and Category must be set in service
    public static Operation toEntity(OperationDTO operationDTO) {
        Operation operation = new Operation();
        operation.setId(operationDTO.getId());
        operation.setOperationType(operationDTO.getOperationType());
        operation.setAmount(operationDTO.getAmount());
        operation.setDateTime(operationDTO.getDateTime());
        operation.setDescription(operationDTO.getDescription());
        return operation;
    }
}
