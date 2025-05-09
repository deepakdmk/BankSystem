package Interest.DTO;

import java.math.BigDecimal;

public class InterestDTO {

    private String date; //same as transaction YYYYMMdd

    private String ruleId;

    private BigDecimal rate;

    public InterestDTO() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
