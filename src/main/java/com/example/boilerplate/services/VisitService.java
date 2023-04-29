package com.example.boilerplate.services;

import com.example.boilerplate.models.Patient;
import com.example.boilerplate.models.Visit;
import com.example.boilerplate.repositories.VisitRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;

    public Visit saveVisit(Visit visit){
        return visitRepository.save(visit);
    }
    public void deleteVisit(Visit visit){
        visitRepository.delete(visit);
    }
    public void deleteById(String id){
        visitRepository.deleteById(id);
    }
    public Visit findById(String id){
        return visitRepository.findById(id).orElse(null);
    }
    public Iterable<Visit> findAll(){
        return visitRepository.findAll();
    }
    public void update(Visit visit){
        visitRepository.update(
                visit.getVisitTime(),
                visit.getApproved(),
                visit.getDoctor().getId(),
                visit.getRelative().getId(),
                visit.getPatient().getId());
    }
    @Transactional
    public List<Visit> findAllByUserId(String id){
        return visitRepository.getVisitsByUserId(id);
    }
@Transactional
    public List<Visit> findAllApprovedByUserId(String id){
        return findAllByUserId(id)
                .stream()
                .filter(Visit::getApproved)
                .collect(Collectors.toList());
    }
    public List<Visit> findAllByHospitalId(String id){
        return visitRepository.findAllByHospitalId(id);
    }

    public List<Visit> findAllNotApprovedByHospitalId(String id){
       return findAllByHospitalId(id).stream().filter(visit -> !visit.getApproved()).collect(Collectors.toList());
    }
}
