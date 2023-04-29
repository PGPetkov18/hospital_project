package com.example.boilerplate.repositories;

import com.example.boilerplate.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User,String>  {
    public User findByUsername(String username);
    public Boolean existsByUsername(String username);

    User findUserById(String id);
@Modifying
@Transactional
    @Query(value = "UPDATE Users " +
            "SET " +
            "Username =:username, " +
            "FirstName=:firstName, " +
            "LastName=:lastName " +
            "WHERE Id=:id",nativeQuery = true)
    void update(String username,String firstName,String lastName,String id);
}
