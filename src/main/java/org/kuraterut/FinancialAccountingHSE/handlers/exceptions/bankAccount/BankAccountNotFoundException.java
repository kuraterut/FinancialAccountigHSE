package org.kuraterut.FinancialAccountingHSE.handlers.exceptions.bankAccount;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
