package com.example.boilerplate.repositories;

import com.example.boilerplate.models.Doctor;
import com.example.boilerplate.models.Patient;
import com.example.boilerplate.models.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReceptionistRepository extends JpaRepository<Receptionist,String> {
     public Receptionist findReceptionistByUserId(String userId);
     @Query(value = "SELECT "+
             "r.Id, " +
             "r.UserId " +
             "FROM Receptionists r " +
             "inner join Users u ON u.Id=r.UserId " +
             "WHERE u.HospitalId=:id ",nativeQuery = true)
     List<Receptionist> findAllByHospitalId(@Param("id")String id);
}
