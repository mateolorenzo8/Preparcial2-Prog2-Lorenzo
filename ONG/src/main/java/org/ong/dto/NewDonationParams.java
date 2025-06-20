package org.ong.dto;

import org.ong.enums.DonorType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NewDonationParams {
    private String donorName;
    private DonorType donorType;
    private BigDecimal amount;
    private LocalDate date;
    private String category;

    public NewDonationParams(String donorName, DonorType donorType, BigDecimal amount, LocalDate date, String category) {
        this.donorName = donorName;
        this.donorType = donorType;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public String getDonorName() {
        return donorName;
    }

    public DonorType getDonorType() {
        return donorType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }
}
