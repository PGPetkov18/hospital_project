package com.example.boilerplate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "Relatives")
public class Relative {
    @Id
    @Column(name = "Id", columnDefinition = "varchar(36)", nullable = false)
    private String id;

    @Column(name = "FirstName", columnDefinition = "nvarchar(100)", nullable = false)
    private String firstName;
    @Column(name = "LastName", columnDefinition = "nvarchar(100)", nullable = false)
    private String lastName;
    @Column(name = "Username", columnDefinition = "varchar(100)", nullable = false)
    private String username;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AddressId", columnDefinition = "varchar(36)", referencedColumnName = "Id")
    private Address address;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "PatientId", referencedColumnName = "Id")
    private Patient patient;

@OneToMany(cascade = CascadeType.ALL,mappedBy = "relative")
private Set<Visit> visits;


    public Relative() {
        id = UUID.randomUUID().toString();
        patient = new Patient();
        address = new Address();
    }


    @Override
    public String toString() {
        return "Relative{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", address=" + address.getId() +
                ", patient=" + patient.getId() +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }
}
