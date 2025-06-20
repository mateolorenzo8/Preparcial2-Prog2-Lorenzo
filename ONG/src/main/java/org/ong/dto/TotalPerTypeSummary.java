package org.ong.dto;

import org.ong.enums.DonorType;

import java.math.BigDecimal;

public class TotalPerTypeSummary {
    private DonorType donorType;
    private long count;
    private BigDecimal total;

    public TotalPerTypeSummary(DonorType donorType, long count, BigDecimal total) {
        this.donorType = donorType;
        this.count = count;
        this.total = total;
    }

    public DonorType getDonorType() {
        return donorType;
    }

    public long getCount() {
        return count;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
