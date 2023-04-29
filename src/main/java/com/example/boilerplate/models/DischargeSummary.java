package com.example.boilerplate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "DischargeSummaries")
public class DischargeSummary {
    @Id
    @Column(name = "Id",columnDefinition = "varchar(36)",nullable = false)
    private String id;

    @Column(name = "FileName",columnDefinition = "varchar(255)",nullable = false)
    private String fileName;
    @Column(name = "FileEntity",columnDefinition = "varbinary(MAX)",nullable = false)
    private byte[] file;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "DoctorId",columnDefinition = "varchar(36)",referencedColumnName = "Id",unique = true)
    private Doctor doctor;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "PatientId",columnDefinition = "varchar(36)",referencedColumnName = "Id",unique = true)
    private Patient patient;

    public DischargeSummary() {
        this.id= UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] temp) {
        this.file = temp;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
