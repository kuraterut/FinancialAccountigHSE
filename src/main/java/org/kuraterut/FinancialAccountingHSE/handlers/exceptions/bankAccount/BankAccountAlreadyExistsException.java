package org.kuraterut.FinancialAccountingHSE.handlers.exceptions.bankAccount;

public class BankAccountAlreadyExistsException extends RuntimeException {
    public BankAccountAlreadyExistsException(String message) {
        super(message);
    }
}
