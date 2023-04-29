package com.example.boilerplate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "Doctors")
public class Doctor {
    @Id
    @Column(name = "Id",nullable = false,columnDefinition = "varchar(36)")
    private String id;
    @Column(name = "Specialization",nullable = false,columnDefinition = "nvarchar(70)")
    private String specialization;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserId")
    private User user;
    public Doctor(){

        this.id= UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", specialization='" + specialization + '\'' +
                ", user=" + user +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(String id) {
        this.id = id;
    }

}
