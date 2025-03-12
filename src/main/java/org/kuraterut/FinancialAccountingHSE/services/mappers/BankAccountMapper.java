package org.kuraterut.FinancialAccountingHSE.services.mappers;

import lombok.NoArgsConstructor;
import org.kuraterut.FinancialAccountingHSE.models.dto.BankAccountDTO;
import org.kuraterut.FinancialAccountingHSE.models.entities.BankAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {
    public static BankAccountDTO toDTO(BankAccount bankAccount) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(bankAccount.getId());
        bankAccountDTO.setName(bankAccount.getName());
        bankAccountDTO.setBalance(bankAccount.getBalance());

        return bankAccountDTO;
    }

    public static BankAccount toEntity(BankAccountDTO bankAccountDTO) {
        return new BankAccount.Builder()
                .setId(bankAccountDTO.getId())
                .setName(bankAccountDTO.getName())
                .setBalance(bankAccountDTO.getBalance())
                .build();

    }

}
