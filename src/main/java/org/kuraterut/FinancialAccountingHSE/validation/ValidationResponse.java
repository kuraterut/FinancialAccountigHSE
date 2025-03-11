package org.kuraterut.FinancialAccountingHSE.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidationResponse {
    private Integer errorCode;
    private String errorMessage;

    public ValidationResponse() {
        errorCode = 200;
        errorMessage = "";
    }
}
