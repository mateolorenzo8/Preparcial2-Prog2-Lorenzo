package org.ong.service;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ong.dto.*;
import org.ong.enums.DonorType;
import org.ong.models.Donation;
import org.ong.utils.HibernateUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ONGTest {
    ONG service;
    Session session;
    Donation donation;
    Donation donation1;

    @BeforeEach
    void setUp() {
        session = HibernateUtil.getSession();
        service = ONG.getInstance();

        donation = new Donation("Juan Lopez", DonorType.INDIVIDUAL, BigDecimal.valueOf(20000), LocalDate.now(), "Salud");

        donation1 = new Donation("Marca Registrada", DonorType.COMPANY, BigDecimal.valueOf(1000000), LocalDate.now(), "Educacion");

        session.beginTransaction();
        session.persist(donation);
        session.persist(donation1);
        session.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
        if (session != null && session.isOpen()) {
            session.beginTransaction();
            session.createQuery("delete from Assignment").executeUpdate();
            session.createQuery("delete from Donation").executeUpdate();
            session.getTransaction().commit();
            session.close();
        }
    }

    @Test
    void newDonation() {
        NewDonationParams params = new NewDonationParams("Juana Gonzalez", DonorType.INDIVIDUAL, BigDecimal.valueOf(20000), LocalDate.now(), "Salud");

        Result res = service.newDonation(params);

        Assertions.assertEquals(true, res.isSuccess());
        Assertions.assertEquals("New donation saved successfully", res.getMessage());
    }

    @Test
    void assingNonExistingDonation() {
        AssignDonationParams params = new AssignDonationParams(8978854, LocalDate.now(), "Salud");

        Result res = service.assignDonation(params);

        Assertions.assertEquals(false, res.isSuccess());
        Assertions.assertEquals("Donation not found", res.getMessage());
    }

    @Test
    void assingReceivedDonation() {
        AssignDonationParams params = new AssignDonationParams(donation.getId(), LocalDate.now(), "Salud");

        service.assignDonation(params);
        Result res = service.assignDonation(params);

        Assertions.assertEquals(false, res.isSuccess());
        Assertions.assertEquals("Donation already received", res.getMessage());
    }

    @Test
    void assingDonationSuccessfully() {
        AssignDonationParams params = new AssignDonationParams(donation.getId(), LocalDate.now(), "Salud");

        Result res = service.assignDonation(params);

        Assertions.assertEquals(true, res.isSuccess());
        Assertions.assertEquals("Assignment saved successfully", res.getMessage());
    }

    @Test
    void getTotalPerType() {
        List<TotalPerTypeSummary> res = service.getTotalPerTypeSummary();

        Assertions.assertEquals(2, res.size());
        Assertions.assertEquals(0, donation1.getAmount().compareTo(res.get(0).getTotal()));
        Assertions.assertEquals(0, donation.getAmount().compareTo(res.get(1).getTotal()));
    }

    @Test
    void getSummaryPerCategory() {
        List<TotalCategoryStatusSummary> res = service.getTotalCategoryStatusSummary();

        Assertions.assertEquals(2, res.size());

        Assertions.assertEquals(0, donation1.getAmount().compareTo(res.get(0).getTotal()));
        Assertions.assertEquals(0, donation.getAmount().compareTo(res.get(1).getTotal()));

        Assertions.assertEquals(1, res.get(0).getCountAssigned());
        Assertions.assertEquals(1, res.get(1).getCountAssigned());
        Assertions.assertEquals(0, res.get(0).getCountReceived());
        Assertions.assertEquals(0, res.get(1).getCountReceived());

        Assertions.assertEquals(donation1.getCategory(), res.get(0).getCategory());
        Assertions.assertEquals(donation.getCategory(), res.get(1).getCategory());
    }
}