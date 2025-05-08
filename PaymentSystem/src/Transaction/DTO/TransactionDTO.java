package Transaction.DTO;

import java.math.BigDecimal;

public class TransactionDTO {

    private String date; // format: YYYYMMDD
    private String transactionId; // format: YYYYMMDD-xx
    private String type; // D, W, or I
    private BigDecimal amount;
    private BigDecimal balanceAfter;

    public TransactionDTO() {
    }

    public TransactionDTO(String date, String type, BigDecimal amount) {
        this.date = date;
        this.type = type.toUpperCase();
        this.amount = amount;
    }

    // Getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type.toUpperCase();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

}
