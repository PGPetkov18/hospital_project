package com.example.boilerplate.repositories;

import com.example.boilerplate.models.Visit;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, String> {
    @Procedure(name = "GetVisitsByUserId")
    List<Visit> getVisitsByUserId(String id);

@Modifying
    @Transactional
    @Query(value = "UPDATE Visits " +
            "SET DoctorId=:doctorId, " +
            "RelativeId=:relativeId, " +
            "IsApproved=:isApproved, " +
            "VisitTime=:visitTime " +
            "WHERE PatientId=:patientId",nativeQuery = true)
    void update(LocalDateTime visitTime,
                boolean isApproved,
                String doctorId,
                String relativeId,
                String patientId);

    @Query(value = "SELECT " +
            "v.Id, " +
            "v.DoctorId, " +
            "v.PatientId, " +
            "v.RelativeId, " +
            "v.VisitTime, " +
            "v.IsApproved " +
            "FROM Visits v " +
            "INNER JOIN Patients p  " +
            "ON v.PatientId = p.Id  " +
            "INNER JOIN Users u " +
            "ON u.Id = p.UserId   " +
            "WHERE u.HospitalId=:id ",nativeQuery = true)
 List<Visit> findAllByHospitalId(String id);
@Modifying
@Transactional
    void deleteByDoctorId(String id);
}

