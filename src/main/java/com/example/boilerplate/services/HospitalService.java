package com.example.boilerplate.services;

import com.example.boilerplate.models.Address;
import com.example.boilerplate.models.Hospital;
import com.example.boilerplate.repositories.AddressRepository;
import com.example.boilerplate.repositories.HospitalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public Hospital saveHospital(Hospital hospital){
        return hospitalRepository.save(hospital);
    }
    public void deleteHospital(Hospital hospital){
        hospitalRepository.delete(hospital);
    }
    public Hospital findById(String id){
        return hospitalRepository.findById(id).orElse(null);
    }
    public Iterable<Hospital> findAll(){
        return hospitalRepository.findAll();
    }
}
