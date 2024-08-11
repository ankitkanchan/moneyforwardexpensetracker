package com.moneyforward.expensetracker.entities;

import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class OutputLedger {

    @JsonProperty("period")
    private final String period;

    @JsonProperty("total_income")
    private final Integer totalIncome;

    @JsonProperty("total_expenditure")
    private final Integer totalExpenditure;

    @JsonProperty("transactions")
    private final List<Transaction> transactions;

    public OutputLedger(final String period, final Integer totalIncome, final Integer totalExpenditure, final List<Transaction> transactions) {
        this.period = period;
        this.totalIncome = totalIncome;
        this.totalExpenditure = totalExpenditure;
        this.transactions = transactions;
    }

    @JsonIgnore
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    @JsonIgnore
    public String getPeriod() {
        return period;
    }


    @JsonIgnore
    public Integer getTotalIncome() {
        return totalIncome;
    }

    @JsonIgnore
    public Integer getTotalExpenditure () {
        return totalExpenditure;
    }

    @Override
    public String toString() {

        final StandardToStringStyle style = new StandardToStringStyle();
        style.setUseClassName(false);
        style.setUseIdentityHashCode(false);
        style.setContentStart(null);
        style.setContentEnd(null);

        return new ToStringBuilder("", style)
                .append("period", period)
                .append("total_income",totalIncome)
                .append("total_expenditure",totalExpenditure)
                .append("transactions",transactions)
                .toString();
    }
}
