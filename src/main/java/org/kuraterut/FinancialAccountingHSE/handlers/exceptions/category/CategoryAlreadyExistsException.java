package org.kuraterut.FinancialAccountingHSE.handlers.exceptions.category;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
