package cleancodecleanarchitecture16.account.domain.vo;

import java.util.UUID;

public record AccountId(String value) {

    public AccountId {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid value for account accountId");
        }
    }

    public static AccountId unique() {
        return new AccountId(UUID.randomUUID().toString());
    }

    public static AccountId with(final String value) {
        try {
            return new AccountId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for account accountId");
        }
    }
}
