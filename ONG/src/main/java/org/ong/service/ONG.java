package org.ong.service;

import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.ong.dto.*;
import org.ong.enums.DonationStatus;
import org.ong.models.Assignment;
import org.ong.models.Donation;
import org.ong.utils.HibernateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class ONG {
    private static volatile ONG instance;

    public static ONG getInstance() {
        if (instance == null) {
            instance = new ONG();
        }
        return instance;
    }

    private ONG() {}

    public Result newDonation(NewDonationParams params) {
        Donation donation = new Donation(params.getDonorName(), params.getDonorType(), params.getAmount(), params.getDate(), params.getCategory());

        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.persist(donation);
            session.getTransaction().commit();
        }

        return new Result(true, "New donation saved successfully");
    }

    public Result assignDonation(AssignDonationParams params) {
        Donation donation;

        try (Session session = HibernateUtil.getSession()) {
            donation = session.get(Donation.class, params.getDonationId());

            if (donation == null) return new Result(false, "Donation not found");
        }

        if (donation.getStatus() == DonationStatus.RECEIVED) return new Result(false, "Donation already received");

        Assignment assignment = new Assignment(donation, params.getAdminNotes(), params.getDate());

        donation.setStatus(DonationStatus.RECEIVED);

        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.persist(assignment);
            session.merge(donation);
            session.getTransaction().commit();
        }

        return new Result(true, "Assignment saved successfully");
    }

    public List<TotalPerTypeSummary> getTotalPerTypeSummary() {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TotalPerTypeSummary> cq = cb.createQuery(TotalPerTypeSummary.class);
            Root<Donation> root = cq.from(Donation.class);

            cq.groupBy(root.get("donorType"));
            cq.orderBy(cb.desc(root.get("amount")));

            cq.select(cb.construct(
                    TotalPerTypeSummary.class,
                    root.get("donorType"),
                    cb.count(root),
                    cb.sum(root.get("amount"))
            ));

            return session.createQuery(cq).getResultList();
        }
    }

    public List<TotalCategoryStatusSummary> getTotalCategoryStatusSummary() {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TotalCategoryStatusSummary> cq = cb.createQuery(TotalCategoryStatusSummary.class);
            Root<Donation> root = cq.from(Donation.class);

            cq.groupBy(root.get("category"));
            cq.orderBy(cb.desc(root.get("amount")));

            Expression<Long> receivedCount = cb.sum(
                    cb.<Long>selectCase()
                            .when(cb.equal(root.get("status"), "RECEIVED"), 1L)
                            .otherwise(0L)
            );

            Expression<Long> assignedCount = cb.sum(
                    cb.<Long>selectCase()
                            .when(cb.equal(root.get("status"), "ASSIGNED"), 1L)
                            .otherwise(0L)
            );

            Expression<BigDecimal> donationCount = cb.sum(root.<BigDecimal>get("amount"));

            cq.select(cb.construct(
                    TotalCategoryStatusSummary.class,
                    root.get("category"),
                    receivedCount,
                    assignedCount,
                    donationCount
            ));

            return session.createQuery(cq).getResultList();
        }
    }
}
