package com.example.boilerplate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Entity
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "Addresses")
public class Address {
    @Id
    @Column(name = "Id", columnDefinition = "varchar(36)",nullable = false)
    private String id;
    @Column(name = "Country", columnDefinition = "nvarchar(100)",nullable = false)
    private String country;
    @Column(name = "City", columnDefinition = "nvarchar(100)",nullable = false)
    private String city;
    @Column(name = "Street", columnDefinition = "nvarchar(100)",nullable = false)
    private String street;
    @Column(name = "Number", columnDefinition = "int CHECK(Number>0)",nullable = false)
    private Integer number;



    public Address() {
        this.id= UUID.randomUUID().toString();
    }



    public String getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
