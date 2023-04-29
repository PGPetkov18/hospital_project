package com.example.boilerplate.repositories;

import com.example.boilerplate.models.Doctor;
import com.example.boilerplate.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,String> {

    Doctor findDoctorByUserId(String userId);

    @Query(value ="SELECT " +
            "d.Id, " +
            "d.Specialization, " +
            "d.UserId " +
            "FROM Doctors d " +
            "inner join Users u ON u.Id=d.UserId "+
            "WHERE u.HospitalId=:id ",nativeQuery = true)
    List<Doctor> findAllByHospitalId(@Param("id") String id);

    Doctor findByUserId(String id);
}
