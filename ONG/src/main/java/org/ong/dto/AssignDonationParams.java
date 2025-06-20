package org.ong.dto;

import java.time.LocalDate;

public class AssignDonationParams {
    private long donationId;
    private LocalDate date;
    private String adminNotes;

    public AssignDonationParams(long donationId, LocalDate date, String adminNotes) {
        this.donationId = donationId;
        this.date = date;
        this.adminNotes = adminNotes;
    }

    public long getDonationId() {
        return donationId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAdminNotes() {
        return adminNotes;
    }
}
