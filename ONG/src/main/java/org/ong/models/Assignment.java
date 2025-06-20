package org.ong.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Table
@Entity(name = "donation_assignment")
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
}
