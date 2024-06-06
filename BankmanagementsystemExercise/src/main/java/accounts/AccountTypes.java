package accounts;

import lombok.Getter;

public enum AccountTypes {
    GIRO("giro"),
    CREDIT("credit"),
    SAVINGS("savings");

    @Getter
    private final String value;

    AccountTypes(String value) {
        this.value = value;
    }
}
