package com.example.boilerplate.repositories;

import com.example.boilerplate.models.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, String> {
    List<Treatment> findAllByPatientId(String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Treatments SET " +
            "Description=:description , " +
            "DateOfTreatment =:dateOfTreatment , " +
            "Name=:name WHERE Id=:id",
            nativeQuery = true)
    void update(String description, LocalDateTime dateOfTreatment, String name, String id);
@Modifying
@Transactional
    void deleteByDoctorId(String id);
}
