package com.example.boilerplate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@AllArgsConstructor
@Entity
@Table(name = "Visits")
public class Visit {
    @Id
    private String id;
@DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    @Column(name = "VisitTime", columnDefinition = "datetime2(0)", nullable = false)
    private LocalDateTime visitTime;
    @Column(name = "IsApproved", columnDefinition = "bit)", nullable = false)
    private Boolean isApproved;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "RelativeId", referencedColumnName = "Id")
    private Relative relative;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "PatientId", referencedColumnName = "Id")
    private Patient patient;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "DoctorId", referencedColumnName = "Id")
    private Doctor doctor;

    public Visit() {
        id= UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id='" + id + '\'' +
                ", visitTime=" + visitTime +
                ", isApproved=" + isApproved +
                ", relative=" + relative.getId() +
                ", patient=" + patient.getId() +
                ", doctor=" + doctor.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(id, visit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public Relative getRelative() {
        return relative;
    }

    public void setRelative(Relative relative) {
        this.relative = relative;
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
}
