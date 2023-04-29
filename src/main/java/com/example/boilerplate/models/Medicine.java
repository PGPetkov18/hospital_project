package com.example.boilerplate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "Medicines")
public class Medicine {
    @Id
    @Column(name = "Id",columnDefinition = "varchar(36)",nullable = false)
    private String id;
    @Column(name = "Name",columnDefinition = "nvarchar(70)",nullable = false)
    private String name;
    @Column(name = "Quantity",columnDefinition = "int",nullable = false)
    private String quantity;
    @Column(name = "Description",columnDefinition = "nvarchar(MAX)",nullable = false)
    private String description;
    public Medicine() {
        this.id= UUID.randomUUID().toString();
        patients = new HashSet<>();
    }
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "PatientsMedicines"
    ,joinColumns = @JoinColumn(name = "MedicineId"),
    inverseJoinColumns = @JoinColumn(name = "PatientId"))
private Set<Patient> patients;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "DoctorId",referencedColumnName = "Id")
    private Doctor doctor;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", description='" + description + '\'' +
                ", doctor=" + doctor.getId() +
                '}';
    }
}
