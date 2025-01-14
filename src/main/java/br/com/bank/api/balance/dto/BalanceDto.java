package br.com.bank.api.balance.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

public class BalanceDto {

    public enum Request{;

        @Data
        public static class Update{

            @NotNull(message = "The field cannot be null")
            @PositiveOrZero(message = "The blocked amount cannot be negative")
            @Digits(integer = 16, fraction = 2, message = "The blocked amount must have up to 16 digits before and 2 digits " +
                    "after the decimal point")
            private BigDecimal blockedAmount;
        }
    }

    public enum Response{;

        @Data
        public static class Balance{
            private UUID id;
            private BigDecimal availableAmount;
            private BigDecimal blockedAmount;
        }

        @Data
        public static class BalanceWithoutId{
            private BigDecimal availableAmount;
            private BigDecimal blockedAmount;
        }
    }
}
