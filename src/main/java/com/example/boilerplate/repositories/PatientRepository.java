package com.example.boilerplate.repositories;


import com.example.boilerplate.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,String> {
    public Patient findPatientByUserId(String userId);

    @Query(value = "SELECT "+
            "p.Id, " +
            "p.DateOfBirth, " +
            "p.Condition," +
            "p.HospitalizationDuration, " +
            "p.SurgeryRequired, " +
            "p.UserId " +
            "FROM Patients p " +
            "inner join Users u ON u.Id=p.UserId " +
            "WHERE u.HospitalId=:id ",nativeQuery = true)
    List<Patient> findAllByHospitalId(@Param("id")String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Patients SET " +
            "DateOfBirth=:dateOfBirth, " +
            " Condition=:condition, " +
            "SurgeryRequired=:surgeryRequired," +
            "HospitalizationDuration=:hospitalizationDuration  " +
            "WHERE Id=:id",nativeQuery = true)
    void update(LocalDate dateOfBirth, String condition, boolean surgeryRequired,int hospitalizationDuration, String id);
@Modifying
@Transactional
@Query(value = "UPDATE Patients SET SurgeryRequired=:surgeryRequired , Condition =:condition WHERE Id=:id",nativeQuery = true)
    void updateFromDoctor(Boolean surgeryRequired, String condition, String id);
}
