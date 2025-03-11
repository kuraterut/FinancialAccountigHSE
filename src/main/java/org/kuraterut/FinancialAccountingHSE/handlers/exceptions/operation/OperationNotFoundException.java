package org.kuraterut.FinancialAccountingHSE.handlers.exceptions.operation;

public class OperationNotFoundException extends RuntimeException {
    public OperationNotFoundException(String message) {
        super(message);
    }
}
