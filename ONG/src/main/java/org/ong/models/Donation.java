package org.ong.models;

import jakarta.persistence.*;
import org.ong.enums.DonationStatus;
import org.ong.enums.DonorType;

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

    @Column(name = "donation_date")
    private LocalDate donationDate;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DonationStatus status;

    public Donation() {}
}
