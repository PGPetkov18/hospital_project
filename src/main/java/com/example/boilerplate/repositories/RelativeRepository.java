package com.example.boilerplate.repositories;


import com.example.boilerplate.models.Patient;
import com.example.boilerplate.models.Relative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelativeRepository extends JpaRepository<Relative,String> {

    @Query(value = "SELECT  " +
            "r.Id,  " +
            "r.AddressId,  " +
            "r.FirstName, " +
            "r.LastName, " +
            "r.Username," +
            "r.PatientId  " +
            "FROM Relatives r  " +
            "INNER JOIN Patients p " +
            "ON r.PatientId = p.Id " +
            "inner join Users u ON u.Id=p.UserId  " +
            "WHERE u.HospitalId=:id",nativeQuery = true)
    List<Relative> findAllByHospitalId(@Param("id")String id);

    List<Relative> findAllByPatientId(String id);

}
