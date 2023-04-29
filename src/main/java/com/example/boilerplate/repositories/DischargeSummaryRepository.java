package com.example.boilerplate.repositories;

import com.example.boilerplate.models.DischargeSummary;
import com.example.boilerplate.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DischargeSummaryRepository extends JpaRepository<DischargeSummary,String> {

    @Query(value ="SELECT " +
            "ds.Id, " +
            "ds.FileEntity, " +
            "ds.FileName, " +
            "ds.DoctorId, " +
            "ds.PatientId " +
            "FROM DischargeSummaries ds " +
            "INNER JOIN Doctors d ON d.Id = ds.DoctorId" +
            " join Users u ON u.Id=d.UserId "+
            "WHERE u.HospitalId=:id ",nativeQuery = true)
    List<DischargeSummary> findAllByHospitalId(@Param("id") String id);

    List<DischargeSummary> findByPatientId(String id);

    @Modifying
    @Transactional
    void deleteByDoctorId(String id);
}
