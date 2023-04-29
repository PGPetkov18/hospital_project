package com.example.boilerplate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
@AllArgsConstructor
@Entity
@Table(name = "Treatments")
public class Treatment {
    @Id
    @Column(name = "Id",columnDefinition = "varchar(36)",nullable = false)
    private String id;
    @Column(name = "Name",columnDefinition = "nvarchar(255)",nullable = false)
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    @Column(name = "DateOfTreatment",columnDefinition = "datetime2(0)",nullable = false)
    private LocalDateTime dateOfTreatment;
    @Column(name = "Description",columnDefinition = "nvarchar(MAX)",nullable = false)
    private String description;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "PatientId",columnDefinition = "varchar(36)",referencedColumnName = "Id")
    private Patient patient;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "DoctorId",columnDefinition = "varchar(36)",referencedColumnName = "Id")
    private Doctor doctor;
    public Treatment() {
        this.id= UUID.randomUUID().toString();
    }


    @Override
    public String toString() {
        return "Treatment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateOfTreatment=" + dateOfTreatment +
                ", description='" + description + '\'' +
                ", patient=" + patient.getId() +
                ", doctor=" + doctor.getId() +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateOfTreatment() {
        return dateOfTreatment;
    }

    public void setDateOfTreatment(LocalDateTime dateOfTreatment) {
        this.dateOfTreatment = dateOfTreatment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Treatment treatment = (Treatment) o;
        return Objects.equals(id, treatment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
