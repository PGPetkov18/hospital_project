package com.example.boilerplate.repositories;

import com.example.boilerplate.models.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital,String> {
}
