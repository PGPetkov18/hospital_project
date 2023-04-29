package com.example.boilerplate.services;

import com.example.boilerplate.models.DischargeSummary;
import com.example.boilerplate.models.Doctor;
import com.example.boilerplate.repositories.DischargeSummaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DischargeSummaryService {
    private final DischargeSummaryRepository dischargeSummaryRepository;



    public DischargeSummary saveDischargeSummary(DischargeSummary dischargeSummary) {
        return dischargeSummaryRepository.save(dischargeSummary);
    }

    public void deleteDischargeSummary(DischargeSummary dischargeSummary) {
        dischargeSummaryRepository.delete(dischargeSummary);
    }
    public void deleteById(String id) {
        dischargeSummaryRepository.deleteById(id);
    }

    public DischargeSummary findById(String id) {
        return dischargeSummaryRepository.findById(id).orElse(null);
    }

    public Iterable<DischargeSummary> findAll() {
        return dischargeSummaryRepository.findAll();
    }

    public List<DischargeSummary> findAllByHospitalId(String id){return dischargeSummaryRepository.findAllByHospitalId(id);}

public List<DischargeSummary> findAllByPatientId(String id){
        return dischargeSummaryRepository.findByPatientId(id);
}
    public boolean checkDischargeSummaryByIdAndHospitalId(String id, String hospitalId) {
        List<DischargeSummary> dischargeSummaries = findAllByHospitalId(hospitalId);
        Map<String, DischargeSummary> hashDischargeSummary = new HashMap<>();

        for (DischargeSummary dischargeSummary : dischargeSummaries) {
            hashDischargeSummary.put(dischargeSummary.getId(), dischargeSummary);
        }

        return hashDischargeSummary.containsKey(id);
    }

    public void deleteByDoctorId(String id) {
        dischargeSummaryRepository.deleteByDoctorId(id);
    }
}
