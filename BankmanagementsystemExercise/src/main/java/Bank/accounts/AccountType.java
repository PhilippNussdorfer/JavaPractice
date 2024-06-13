package Bank.accounts;

import lombok.Getter;

public enum AccountType {
    GIRO("giro"),
    CREDIT("credit"),
    SAVINGS("savings");

    @Getter
    private final String value;

    AccountType(String value) {
        this.value = value;
    }
}
