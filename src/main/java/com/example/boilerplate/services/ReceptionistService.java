package com.example.boilerplate.services;

import com.example.boilerplate.models.Doctor;
import com.example.boilerplate.models.Hospital;
import com.example.boilerplate.models.Patient;
import com.example.boilerplate.models.Receptionist;
import com.example.boilerplate.repositories.HospitalRepository;
import com.example.boilerplate.repositories.ReceptionistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReceptionistService {
    private final ReceptionistRepository receptionistRepository;

    public Receptionist saveReceptionist(Receptionist receptionist){
        return receptionistRepository.save(receptionist);
    }
    public void deleteReceptionist(Receptionist receptionist){
        receptionistRepository.delete(receptionist);
    }
    public Receptionist findById(String id){
        return receptionistRepository.findById(id).orElse(null);
    }
    public List<Receptionist> findAll(){
        return receptionistRepository.findAll();
    }
    public void deleteById(String id){
        receptionistRepository.deleteById(id);
    }
    public List<Receptionist> findAllByHospitalId(String id){return receptionistRepository.findAllByHospitalId(id);}
    public boolean checkReceptionistByIdAndHospitalId(String id, String hospitalId) {
        List<Receptionist> receptionists = findAllByHospitalId(hospitalId);
        Map<String, Receptionist> hashPatient = new HashMap<>();

        for (Receptionist receptionist : receptionists) {
            hashPatient.put(receptionist.getId(), receptionist);
        }

        return hashPatient.containsKey(id);
    }

}
