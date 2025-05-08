package Transaction;

import Exception.GeneralException;
import Transaction.DTO.TransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        transaction = new Transaction();
    }

    @Test
    public void testValidDeposit() throws GeneralException {
        String input = "20250101 ACC100 d 100";
        transaction.transactionHandler(input);

        List<TransactionDTO> txns = getTxns("ACC100");
        assertEquals(1, txns.size());
        assertEquals("d", txns.get(0).getType());
        assertEquals("100", txns.get(0).getAmount().toPlainString());
        assertEquals("100", txns.get(0).getBalanceAfter().toPlainString());
    }

    @Test
    public void testValidWithdrawal() throws GeneralException {
        transaction.transactionHandler("20250101 ACC101 d 200");
        transaction.transactionHandler("20250102 ACC101 w 50");

        List<TransactionDTO> txns = getTxns("ACC101");
        assertEquals(2, txns.size());
        assertEquals("150", txns.get(1).getBalanceAfter().toPlainString());
    }

    @Test
    public void testOverdraftShouldFail() {
        Exception exception = assertThrows(GeneralException.class, () -> {
            transaction.transactionHandler("20250101 ACC200 w 50");
        });
        assertTrue(exception.getMessage().contains("negative"));
    }

    @Test
    public void testBackdatedDepositAffectsFutureBalance() throws GeneralException {
        transaction.transactionHandler("20250101 ACC300 d 100");
        transaction.transactionHandler("20241230 ACC300 d 100"); // backdated deposit

        List<TransactionDTO> txns = getTxns("ACC300");
        assertEquals(2, txns.size());
        assertEquals("100", txns.get(0).getBalanceAfter().toPlainString());
        assertEquals("200", txns.get(1).getBalanceAfter().toPlainString());
    }

    private List<TransactionDTO> getTxns(String accountNumber) {
        return transaction.getClass()
                .getDeclaredAnnotation(Transaction.class)
                .getTransactions(accountNumber);
    }
}
