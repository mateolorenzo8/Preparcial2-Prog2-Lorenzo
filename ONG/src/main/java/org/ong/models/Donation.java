package org.ong.models;

import jakarta.persistence.*;
import org.ong.enums.DonationStatus;
import org.ong.enums.DonorType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "donation")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "donor_name", length = 100)
    private String donorName;

    @Column(name = "donor_type")
    @Enumerated(EnumType.STRING)
    private DonorType donorType;

    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "donation_date")
    private LocalDate donationDate;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DonationStatus status;

    public Donation() {}

    public Donation(String donorName, DonorType donorType, BigDecimal amount, LocalDate donationDate, String category) {
        this.donorName = donorName;
        this.donorType = donorType;
        this.amount = amount;
        this.donationDate = donationDate;
        this.category = category;
        this.status = DonationStatus.ASSIGNED;
    }

    public long getId() {
        return id;
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

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public String getCategory() {
        return category;
    }

    public DonationStatus getStatus() {
        return status;
    }

    public void setStatus(DonationStatus status) {
        this.status = status;
    }
}
