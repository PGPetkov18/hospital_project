package com.example.boilerplate.repositories;

import com.example.boilerplate.BoilerplateApplication;
import com.example.boilerplate.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BoilerplateApplication.class)
public class DischargeSummaryRepositoryTest {
    @Autowired
    private DischargeSummaryRepository repository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserRepository userRepository;
    private Address address;
    private Hospital hospital;
    private User userDoctor;
    private User patientUser;
    private Doctor doctor;
    private Patient patient;

    public boolean setUp() {

        address = new Address();
        address.setCity("Burgas");
        address.setNumber(400);
        address.setCountry("Bulgaria");
        address.setStreet("Zornica");
       assertNotNull(addressRepository.save(address));
        hospital = new Hospital();
        hospital.setName("Deva Maria");
        hospital.setAddress(address);
        assertNotNull(hospitalRepository.save(hospital));
        //doctor
        userDoctor = new User();
        userDoctor.setFirstName("Ivan");
        userDoctor.setHospital(hospital);
        userDoctor.setIsAdmin(false);
        userDoctor.setLastName("Ivanov");
        userDoctor.setUsername("Milko");
        userDoctor.setPassword("strong password");
        assertNotNull(userRepository.save(userDoctor));
        //patient
        patientUser = new User();
        patientUser.setFirstName("Misho");
        patientUser.setHospital(hospital);
        patientUser.setIsAdmin(false);
        patientUser.setLastName("Petrov");
        patientUser.setUsername("Misho123");
        patientUser.setPassword("silna parola");
        assertNotNull(userRepository.save(patientUser));
this.doctor=new Doctor();
        this.doctor.setUser(userDoctor);
        this.patient=new Patient();
        patient.setUser(patientUser);
        patient.setCondition("Zle");
        patient.setDateOfBirth(LocalDate.now());
        patient.setSurgeryRequired(false);


        return true;
    }

    @Test
    public void dischargeSummaryRepositoryTest() {
        byte[] file = {0x01, 0x02, 0x03};

        DischargeSummary summary=new DischargeSummary();
        summary.setDoctor(doctor);
        summary.setPatient(patient);
        summary.setFile(file);
repository.save(summary);
    }
}
