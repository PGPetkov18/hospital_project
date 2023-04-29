package com.example.boilerplate.services;

import com.example.boilerplate.models.Hospital;
import com.example.boilerplate.models.User;
import com.example.boilerplate.repositories.HospitalRepository;
import com.example.boilerplate.repositories.TreatmentRepository;
import com.example.boilerplate.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;



    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void update(String username, String firstName, String lastName,String id){
        userRepository.update(
                username,
                firstName,
                lastName,
                id);
    }
    public void deleteUser(User user){
        userRepository.delete(user);
    }
    public User findById(String id){
        return userRepository.findById(id).orElse(null);
    }
    public Iterable<User> findAll(){
        return userRepository.findAll();
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public boolean checkPassword(User user, User loginUser) {
        return user.getPassword().equals(loginUser.getPassword());
    }
    public boolean checkUsername(User user){
        return userRepository.existsByUsername(user.getUsername());
    }

}
