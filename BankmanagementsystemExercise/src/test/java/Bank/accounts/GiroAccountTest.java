package Bank.accounts;

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
class GiroAccountTest {

    GiroAccount giroAccount;

    @Mock
    AccountType giro;
    @Mock
    Customer customer;
    @Mock
    Customer customer_2;
    @Mock
    Account account;

    @BeforeEach
    void setUp() {
        giroAccount = new GiroAccount(1250.0, giro, 250.0);
    }

    @Test
    void withdraw() {
        assertFalse(giroAccount.withdraw(-250));
        assertFalse(giroAccount.withdraw(0));
        assertTrue(giroAccount.withdraw(250));

        assertFalse(giroAccount.withdraw(1260));
        assertTrue(giroAccount.withdraw(1250));
        assertFalse(giroAccount.withdraw(200));

        assertEquals(-250.0, giroAccount.balance);
    }

    @Test
    void transfer() {
        assertFalse(giroAccount.transfer(0, 1L, List.of(customer, customer_2), "credit"));
        assertFalse(giroAccount.transfer(-100, 1L, List.of(customer, customer_2), "credit"));

        Mockito.when(customer.getCustomerID()).thenReturn(1L);
        Mockito.when(customer_2.getCustomerID()).thenReturn(2L);
        assertFalse(giroAccount.transfer(100, 3L, List.of(customer, customer_2), "credit"));

        Mockito.when(customer_2.getAccount("credit")).thenReturn(null);
        assertFalse(giroAccount.transfer(100, 2L, List.of(customer, customer_2), "credit"));

        Mockito.when(customer_2.getAccount("credit")).thenReturn(account);
        assertTrue(giroAccount.transfer(100, 2L, List.of(customer, customer_2), "credit"));
        assertEquals(1150.0, giroAccount.balance);
        Mockito.verify(account, Mockito.times(1)).deposit(100);

        assertFalse(giroAccount.transfer(1600, 2L, List.of(customer, customer_2), "credit"));
    }

    @Test
    void setLimit() {
        giroAccount.setLimit(500.0);
        assertEquals(-500.0, giroAccount.getLimit());

        giroAccount.setLimit(-250.0);
        assertEquals(-250.0, giroAccount.getLimit());
    }

    @Test
    void getter() {
        assertEquals(giro, giroAccount.getAccountType());
    }

    @Test
    void deposit() {
        giroAccount.deposit(-250);
        assertEquals(1500.0, giroAccount.balance);

        giroAccount.deposit(250);
        assertEquals(1750.0, giroAccount.balance);
    }
}