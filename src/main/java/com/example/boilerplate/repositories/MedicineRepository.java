package com.example.boilerplate.repositories;

import com.example.boilerplate.models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine,String> {
    @Query(value = "SELECT " +
            "m.Id, " +
            "m.Name, " +
            "m.Quantity, " +
            "m.Description," +
            "m.DoctorId " +
            "FROM Medicines m " +
            "INNER JOIN PatientsMedicines PM " +
            "on m.Id = PM.MedicineId " +
            "WHERE PM.PatientId=:id ",nativeQuery = true)
    List<Medicine> findAllByPatientId(String id);
    @Modifying
    @Transactional
    @Query(value = "UPDATE Medicines " +
            "SET Description=:description , " +
            "Quantity=:quantity , Name=:name WHERE Id=:id",nativeQuery = true)
    void update(String description, String quantity, String name, String id);
@Query(value = "SELECT  " +
        "m.Id, " +
        "m.Name, " +
        "m.Quantity, " +
        "m.Description," +
        "m.DoctorId " +
        "FROM Medicines m " +
        "INNER JOIN Doctors d " +
        "ON m.DoctorId=d.Id " +
        "INNER JOIN Users u " +
        "ON u.Id = d.UserId " +
        "WHERE u.HospitalId =:id",nativeQuery = true)
    List<Medicine> findAllByHospitalId(String id);

    List<Medicine> findAllByDoctorId(String id);
@Transactional
@Modifying
@Query(value = "DELETE FROM PatientsMedicines WHERE MedicineId=:id",nativeQuery = true)
    void deleteMappingById(String id);
}
