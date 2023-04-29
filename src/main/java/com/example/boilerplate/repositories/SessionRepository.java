package com.example.boilerplate.repositories;

import com.example.boilerplate.models.Relative;
import com.example.boilerplate.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session,String> {
    Session findByUserId(String id);

}
