package com.moneyforward.expensetracker.entities;
import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Transaction {

    @JsonProperty("date")
    private final String date;
    @JsonProperty("amount")
    private final Integer amount;
    @JsonProperty("content")
    private final String content;

    public Transaction(final String date, final Integer amount, final String content){
        this.date = date;
        this.amount = amount;
        this.content = content;
    }

    @JsonIgnore
    public String getDate() {
        return date;
    }

    @JsonIgnore
    public Integer getAmount() {
        return amount;
    }

    @JsonIgnore
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {

        final StandardToStringStyle style = new StandardToStringStyle();
        style.setUseClassName(false);
        style.setUseIdentityHashCode(false);
        style.setContentStart(null);
        style.setContentEnd(null);

        return new ToStringBuilder("", style).append("date", date)
                .append("amount", amount).append("content", content)
                .toString();
    }




}
