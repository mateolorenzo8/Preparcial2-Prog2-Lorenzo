package org.ong.dto;

import java.math.BigDecimal;

public class TotalCategoryStatusSummary {
    private String category;
    private long countReceived;
    private long countAssigned;
    private BigDecimal total;

    public TotalCategoryStatusSummary(String category, long countReceived, long countAssigned, BigDecimal total) {
        this.category = category;
        this.countReceived = countReceived;
        this.countAssigned = countAssigned;
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public long getCountReceived() {
        return countReceived;
    }

    public long getCountAssigned() {
        return countAssigned;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
