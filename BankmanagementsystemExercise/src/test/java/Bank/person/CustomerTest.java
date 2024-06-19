package Bank.person;

import Bank.customExceptions.AccountTypeAlreadyExists;
import Bank.customExceptions.AccountTypeNotExisting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerTest {

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("hans", "2.2.24", 12L, 12L, "1122");
    }

    @Test
    void addAccount() throws AccountTypeAlreadyExists, AccountTypeNotExisting {
        customer.addAccount("credit");
        customer.addAccount("giro");

        assertEquals("giro", customer.getAccount("giro").getAccountType().getValue());
        assertEquals("credit", customer.getAccount("credit").getAccountType().getValue());
        assertEquals(2, customer.getAccounts().size());

        customer.addAccount("savings");

        assertEquals("savings", customer.getAccount("savings").getAccountType().getValue());
        assertEquals(3, customer.getAccounts().size());
    }

    @Test
    void getter() {
        assertEquals(12L, customer.getCustomerID());
    }

    @Test
    void exceptions() throws AccountTypeAlreadyExists, AccountTypeNotExisting {
        customer.addAccount("giro");
        assertThrows(AccountTypeAlreadyExists.class, ()-> customer.addAccount("giro"));
        assertThrows(AccountTypeNotExisting.class, ()-> customer.addAccount("garo"));
        assertThrows(AccountTypeNotExisting.class, ()-> customer.getAccount("credit"));
    }
}