package com.example.boilerplate.services;

import com.example.boilerplate.models.Hospital;
import com.example.boilerplate.models.Patient;
import com.example.boilerplate.models.Relative;
import com.example.boilerplate.repositories.HospitalRepository;
import com.example.boilerplate.repositories.RelativeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RelativeService {
    private final RelativeRepository relativeRepository;

    public Relative save(Relative relative){
        return relativeRepository.save(relative);
    }
    public void deleteRelative(Relative relative){
        relativeRepository.delete(relative);
    }
    public void deleteById(String id){
        relativeRepository.deleteById(id);
    }

    public List<Relative> findAllByHospitalId(String hospitalId){
        return relativeRepository.findAllByHospitalId(hospitalId);
    }
    public boolean checkPatientByIdAndHospitalId(String id,  String hospitalId){
        List<Relative> relatives = findAllByHospitalId(hospitalId);
        Map<String, Relative> hashPatient = new HashMap<>();

        for (Relative patient : relatives) {
            hashPatient.put(patient.getId(), patient);
        }

        return hashPatient.containsKey(id);
    }
    public Relative findById(String id){
        return relativeRepository.findById(id).orElse(null);
    }
    public List<Relative> findAll(){
        return relativeRepository.findAll();
    }

    public List<Relative> findAllByPatientId(String id){
        return relativeRepository.findAllByPatientId(id);
    }

}
