package bankAccount.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.bankAccount.BankAccountNotFoundException;
import org.kuraterut.FinancialAccountingHSE.models.dto.BankAccountDTO;
import org.kuraterut.FinancialAccountingHSE.models.entities.BankAccount;
import org.kuraterut.FinancialAccountingHSE.repositories.BankAccountRepository;
import org.kuraterut.FinancialAccountingHSE.services.BankAccountService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class BankAccountServiceDeleteTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    private BankAccount bankAccount;
    private BankAccountDTO bankAccountDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setName("Test Account");
        bankAccount.setBalance(100.0);
        bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(1L);
        bankAccountDTO.setName("Test Account");
        bankAccountDTO.setBalance(100.0);
    }

    // Test for deleteBankAccount
    @Test
    void testDeleteBankAccount_Success() {
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));

        bankAccountService.deleteBankAccount(1L);

        verify(bankAccountRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBankAccount_NotFound() {
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BankAccountNotFoundException.class, () -> {
            bankAccountService.deleteBankAccount(1L);
        });

        assertEquals("Bank account not found by ID 1", exception.getMessage());
    }
}