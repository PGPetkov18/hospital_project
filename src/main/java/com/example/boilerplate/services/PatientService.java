package com.example.boilerplate.services;

import com.example.boilerplate.models.Hospital;
import com.example.boilerplate.models.Patient;
import com.example.boilerplate.repositories.HospitalRepository;
import com.example.boilerplate.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public void update(Patient patient,String id){
        patientRepository.update(
                patient.getDateOfBirth(),
                patient.getCondition(),
                patient.getSurgeryRequired(),
                patient.getHospitalizationDuration(),
                id);
    }

    public Patient savePatient(Patient patient){
        return patientRepository.save(patient);
    }
    public void deletePatient(Patient patient){
        patientRepository.delete(patient);
    }
    public Patient findById(String id){
        return patientRepository.findById(id).orElse(null);
    }
    public List<Patient> findAll(){
        return patientRepository.findAll();
    }
    public void deleteById(String id){
        patientRepository.deleteById(id);
    }
    public List<Patient> findAllByHospitalId(String id){return patientRepository.findAllByHospitalId(id);}
    public boolean checkPatientByIdAndHospitalId(String id, String hospitalId) {
        List<Patient> patients = findAllByHospitalId(hospitalId);
        Map<String, Patient> hashPatient = new HashMap<>();

        for (Patient patient : patients) {
            hashPatient.put(patient.getId(), patient);
        }

        return hashPatient.containsKey(id);
    }

    public Patient findByUserId(String id){
        return patientRepository.findPatientByUserId(id);
    }

    public void updateFromDoctor(Patient patient){
        patientRepository.updateFromDoctor(patient.getSurgeryRequired(),patient.getCondition(),patient.getId());
    }
}
