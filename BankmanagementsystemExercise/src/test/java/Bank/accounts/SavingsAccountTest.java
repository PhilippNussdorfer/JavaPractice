package Bank.accounts;

import Bank.customExceptions.AccountTypeNotExisting;
import Bank.person.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SavingsAccountTest {

    SavingsAccount savingsAccount;

    @Mock
    AccountType savings;
    @Mock
    Customer customer;
    @Mock
    Customer customer_2;
    @Mock
    Account account;

    @BeforeEach
    void setUp() {
        savingsAccount = new SavingsAccount(1250.0, savings);
    }

    @Test
    void withdraw() {
        assertTrue(savingsAccount.withdraw(250));
        assertFalse(savingsAccount.withdraw(-250));
        assertFalse(savingsAccount.withdraw(0));

        assertEquals(1000.0, savingsAccount.balance);

        assertFalse(savingsAccount.withdraw(1010));
        assertTrue(savingsAccount.withdraw(1000));

        assertEquals(0.0, savingsAccount.balance);
    }

    @Test
    void transfer() throws AccountTypeNotExisting {
        assertFalse(savingsAccount.transfer(0, 1L, List.of(customer, customer_2), "credit"));
        assertFalse(savingsAccount.transfer(-100, 1L, List.of(customer, customer_2), "credit"));

        Mockito.when(customer.getCustomerID()).thenReturn(1L);
        Mockito.when(customer_2.getCustomerID()).thenReturn(2L);
        assertFalse(savingsAccount.transfer(100, 3L, List.of(customer, customer_2), "credit"));

        Mockito.when(customer_2.getAccount("credit")).thenReturn(null);
        assertFalse(savingsAccount.transfer(100, 2L, List.of(customer, customer_2), "credit"));

        Mockito.when(customer_2.getAccount("credit")).thenReturn(account);
        assertTrue(savingsAccount.transfer(100, 2L, List.of(customer, customer_2), "credit"));
        assertEquals(1150.0, savingsAccount.balance);
        Mockito.verify(account, Mockito.times(1)).deposit(100);

        assertFalse(savingsAccount.transfer(1600, 2L, List.of(customer, customer_2), "credit"));
        assertTrue(savingsAccount.transfer(1150, 2L, List.of(customer, customer_2), "credit"));
        assertEquals(0.0, savingsAccount.balance);
        Mockito.verify(account, Mockito.times(1)).deposit(1150);

        assertFalse(savingsAccount.transfer(100, 1L, List.of(customer, customer_2), "credit"));
    }

    @Test
    void getter() {
        assertEquals(savings, savingsAccount.getAccountType());
    }

    @Test
    void deposit() {
        savingsAccount.deposit(-250);
        assertEquals(1500.0, savingsAccount.balance);

        savingsAccount.deposit(250);
        assertEquals(1750.0, savingsAccount.balance);
    }
}