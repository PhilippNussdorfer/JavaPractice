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
class CreditAccountTest {

    CreditAccount creditAccount;

    @Mock
    AccountType credit;
    @Mock
    Customer customer;
    @Mock
    Customer customer_2;
    @Mock
    Account account;

    @BeforeEach
    void setUp() {
        creditAccount = new CreditAccount(1250.0, credit);
    }

    @Test
    void withdraw() {
        assertTrue(creditAccount.withdraw(250));

        assertFalse(creditAccount.withdraw(-250));
        assertFalse(creditAccount.withdraw(0));

        assertEquals(1000.0, creditAccount.balance);
    }

    @Test
    void deposit() {
        creditAccount.deposit(-250);
        assertEquals(1500.0, creditAccount.balance);

        creditAccount.deposit(250);
        assertEquals(1750.0, creditAccount.balance);
    }

    @Test
    void getter() {
        assertEquals(credit, creditAccount.getAccountType());
    }

    @Test
    void transfer() throws AccountTypeNotExisting {
        assertFalse(creditAccount.transfer(0, 1L, List.of(customer, customer_2), "credit"));
        assertFalse(creditAccount.transfer(-100, 1L, List.of(customer, customer_2), "credit"));

        Mockito.when(customer.getCustomerID()).thenReturn(1L);
        Mockito.when(customer_2.getCustomerID()).thenReturn(2L);
        assertFalse(creditAccount.transfer(100, 3L, List.of(customer, customer_2), "credit"));

        Mockito.when(customer_2.getAccount("credit")).thenReturn(null);
        assertFalse(creditAccount.transfer(100, 2L, List.of(customer, customer_2), "credit"));

        Mockito.when(customer_2.getAccount("credit")).thenReturn(account);
        assertTrue(creditAccount.transfer(100, 2L, List.of(customer, customer_2), "credit"));
        assertEquals(1150.0, creditAccount.balance);
        Mockito.verify(account, Mockito.times(1)).deposit(100);
    }
}