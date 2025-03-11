package bankAccount.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kuraterut.FinancialAccountingHSE.models.dto.BankAccountDTO;
import org.kuraterut.FinancialAccountingHSE.models.entities.BankAccount;
import org.kuraterut.FinancialAccountingHSE.repositories.BankAccountRepository;
import org.kuraterut.FinancialAccountingHSE.services.BankAccountService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceGettingAllTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    @Test
    public void testGetAllBankAccounts_WithData() {
        // Подготовка данных
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(1L);
        bankAccount1.setName("Account 1");
        bankAccount1.setBalance(1000.0);

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setId(2L);
        bankAccount2.setName("Account 2");
        bankAccount2.setBalance(2000.0);

        List<BankAccount> bankAccounts = Arrays.asList(bankAccount1, bankAccount2);

        // Мокируем вызов репозитория
        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);

        // Вызов метода
        List<BankAccountDTO> result = bankAccountService.getAllBankAccounts();

        // Проверки
        assertNotNull(result);
        assertEquals(2, result.size());

        BankAccountDTO dto1 = result.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals("Account 1", dto1.getName());
        assertEquals(1000.0, dto1.getBalance());

        BankAccountDTO dto2 = result.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals("Account 2", dto2.getName());
        assertEquals(2000.0, dto2.getBalance());

        // Проверка вызова
        verify(bankAccountRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllBankAccounts_EmptyList() {
        // Мокируем вызов репозитория
        when(bankAccountRepository.findAll()).thenReturn(Collections.emptyList());

        // Вызов метода
        List<BankAccountDTO> result = bankAccountService.getAllBankAccounts();

        // Проверки
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Проверка вызова
        verify(bankAccountRepository, times(1)).findAll();
    }
}


