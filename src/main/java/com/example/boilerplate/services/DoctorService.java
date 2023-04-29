package com.example.boilerplate.services;

import com.example.boilerplate.models.Address;
import com.example.boilerplate.models.Doctor;
import com.example.boilerplate.models.Patient;
import com.example.boilerplate.repositories.AddressRepository;
import com.example.boilerplate.repositories.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {
    private  DoctorRepository doctorRepository;

    public Doctor saveDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }
    public void deleteDoctor(Doctor doctor){
        doctorRepository.delete(doctor);
    }
    public Doctor findById(String id){
        return doctorRepository.findById(id).orElse(null);
    }
    public Doctor findByUserId(String id){
        return doctorRepository.findByUserId(id);
    }
    public void deleteById(String id){
        doctorRepository.deleteById(id);
    }
    public List<Doctor> findAllByHospitalId(String id){return doctorRepository.findAllByHospitalId(id);}

    public boolean checkDoctorByIdAndHospitalId(String id, String hospitalId) {
        List<Doctor> doctors = findAllByHospitalId(hospitalId);
        Map<String, Doctor> hashDoctor = new HashMap<>();

        for (Doctor doctor : doctors) {
            hashDoctor.put(doctor.getId(), doctor);
        }

        return hashDoctor.containsKey(id);
    }
}
