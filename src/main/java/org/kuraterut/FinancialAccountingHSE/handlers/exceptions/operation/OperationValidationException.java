package org.kuraterut.FinancialAccountingHSE.handlers.exceptions.operation;

public class OperationValidationException extends RuntimeException {
    public OperationValidationException(String message) {
        super(message);
    }
}
