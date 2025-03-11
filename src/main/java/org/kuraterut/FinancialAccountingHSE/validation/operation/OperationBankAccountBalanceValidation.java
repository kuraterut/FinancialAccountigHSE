package org.kuraterut.FinancialAccountingHSE.validation.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.kuraterut.FinancialAccountingHSE.models.entities.Operation;
import org.kuraterut.FinancialAccountingHSE.utils.OperationType;
import org.kuraterut.FinancialAccountingHSE.validation.Validation;
import org.kuraterut.FinancialAccountingHSE.validation.ValidationResponse;

@Getter
@Setter
@AllArgsConstructor
public class OperationBankAccountBalanceValidation implements Validation {
    Operation operation;

    @Override
    public ValidationResponse validate() {
        if(operation.getOperationType() == OperationType.EXPENSE &&
                operation.getBankAccount().getBalance() < operation.getAmount()){
            return new ValidationResponse(400, "Insufficient balance for operation");
        }
        return new ValidationResponse();
    }
}
