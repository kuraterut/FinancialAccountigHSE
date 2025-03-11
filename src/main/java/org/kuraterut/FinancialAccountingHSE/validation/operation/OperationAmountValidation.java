package org.kuraterut.FinancialAccountingHSE.validation.operation;

import lombok.AllArgsConstructor;
import org.kuraterut.FinancialAccountingHSE.models.entities.Operation;
import org.kuraterut.FinancialAccountingHSE.validation.Validation;
import org.kuraterut.FinancialAccountingHSE.validation.ValidationResponse;

@AllArgsConstructor
public class OperationAmountValidation implements Validation {
    private Operation operation;
    @Override
    public ValidationResponse validate() {
        if(operation.getAmount() == null || operation.getAmount() < 0){
            return new ValidationResponse(400, "Operation amount must be greater than or equal to 0");
        }
        return new ValidationResponse();
    }
}
