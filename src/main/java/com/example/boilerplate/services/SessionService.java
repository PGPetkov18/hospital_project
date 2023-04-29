package com.example.boilerplate.services;

import com.example.boilerplate.models.Relative;
import com.example.boilerplate.models.Session;
import com.example.boilerplate.repositories.RelativeRepository;
import com.example.boilerplate.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    public Session saveSession(Session session){
        return sessionRepository.save(session);
    }
    public void deleteSession(Session session){
        sessionRepository.delete(session);
    }
    public Session findById(String id){
        return sessionRepository.findById(id).orElse(null);
    }
    public void deleteById(String id){sessionRepository.deleteById(id);}
    public Iterable<Session> findAll(){
        return sessionRepository.findAll();
    }
    public Session findByUserId(String userId) {
        return sessionRepository.findByUserId(userId);
    }
}
