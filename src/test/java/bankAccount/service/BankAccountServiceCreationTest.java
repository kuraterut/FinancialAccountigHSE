package bankAccount.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kuraterut.FinancialAccountingHSE.handlers.exceptions.bankAccount.BankAccountAlreadyExistsException;
import org.kuraterut.FinancialAccountingHSE.models.dto.BankAccountDTO;
import org.kuraterut.FinancialAccountingHSE.models.entities.BankAccount;
import org.kuraterut.FinancialAccountingHSE.repositories.BankAccountRepository;
import org.kuraterut.FinancialAccountingHSE.services.BankAccountService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceCreationTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    @Test
    public void testCreateBankAccount_Success() {
        // Подготовка данных
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setName("Test Account");
        bankAccountDTO.setBalance(1000.0);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(99L);
        bankAccount.setName("Test Account");
        bankAccount.setBalance(1000.0);

        // Мокируем вызовы репозитория
        when(bankAccountRepository.findByName("Test Account")).thenReturn(Optional.empty());
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        // Вызов метода
        BankAccountDTO result = bankAccountService.createBankAccount(bankAccountDTO);

        // Проверки
        assertNotNull(result);
        assertEquals(99L, result.getId());
        assertEquals("Test Account", result.getName());
        assertEquals(1000.0, result.getBalance());

        // Проверка вызовов
        verify(bankAccountRepository, times(1)).findByName("Test Account");
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
    }

    @Test
    public void testCreateBankAccount_AlreadyExists() {
        // Подготовка данных
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setName("Existing Account");
        bankAccountDTO.setBalance(2000.0);

        BankAccount existingBankAccount = new BankAccount();
        existingBankAccount.setId(2L);
        existingBankAccount.setName("Existing Account");
        existingBankAccount.setBalance(2000.0);

        // Мокируем вызовы репозитория
        when(bankAccountRepository.findByName("Existing Account")).thenReturn(Optional.of(existingBankAccount));

        // Вызов метода и проверка исключения
        BankAccountAlreadyExistsException exception = assertThrows(
                BankAccountAlreadyExistsException.class,
                () -> bankAccountService.createBankAccount(bankAccountDTO)
        );

        // Проверка сообщения исключения
        assertEquals("Bank with name 'Existing Account' already exists", exception.getMessage());

        // Проверка вызовов
        verify(bankAccountRepository, times(1)).findByName("Existing Account");
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }
}
