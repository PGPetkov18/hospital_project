package com.example.boilerplate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Patients")
@AllArgsConstructor
public class Patient {
    @Id
    @Column(name = "Id",columnDefinition = "varchar(36)",nullable = false)
    private String id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DateOfBirth",columnDefinition = "date",nullable = false)
    private LocalDate dateOfBirth;
    @Column(name = "Condition",columnDefinition = "nvarchar(255)",nullable = false)
    private String condition;
    @Column(name = "HospitalizationDuration")
    private int hospitalizationDuration;
    @Column(name = "SurgeryRequired",columnDefinition = "bit",nullable = false)
    private Boolean surgeryRequired;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserId",columnDefinition = "varchar(36)", unique = true)
    private User user;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "PatientsMedicines"
            ,joinColumns = @JoinColumn(name = "PatientId"),
            inverseJoinColumns = @JoinColumn(name = "MedicineId"))
    private Set<Medicine> medicines;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "patient")
    private Set<Relative> relatives;
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
    private Set<Treatment> treatments;

    public Patient() {
        this.id= UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", condition='" + condition + '\'' +
                ", hospitalizationDuration=" + hospitalizationDuration +
                ", surgeryRequired=" + surgeryRequired +
                ", user=" + user.getId() +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Boolean getSurgeryRequired() {
        return surgeryRequired;
    }

    public void setSurgeryRequired(Boolean surgeryRequired) {
        this.surgeryRequired = surgeryRequired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(Set<Medicine> medicines) {
        this.medicines = medicines;
    }

    public int getHospitalizationDuration() {
        return hospitalizationDuration;
    }

    public void setHospitalizationDuration(int hospitalizationDuration) {
        this.hospitalizationDuration = hospitalizationDuration;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Set<Relative> getRelatives() {
        return relatives;
    }

    public void setRelatives(Set<Relative> relatives) {
        this.relatives = relatives;
    }
}
