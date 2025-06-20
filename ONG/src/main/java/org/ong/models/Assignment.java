package org.ong.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "donation_assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "donation_id")
    private Donation donation;

    @Column(name = "notes")
    private String notes;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    public Assignment() {}

    public Assignment(Donation donation, String notes, LocalDate assignedDate) {
        this.donation = donation;
        this.notes = notes;
        this.assignedDate = assignedDate;
    }
}
