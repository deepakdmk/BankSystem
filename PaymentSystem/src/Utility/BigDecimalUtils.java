package Utility;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static boolean ifBgThanZero(BigDecimal balance) {
        return balance != null && balance.compareTo(BigDecimal.ZERO) >= 0;
    }
}
