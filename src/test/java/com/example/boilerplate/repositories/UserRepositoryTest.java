//package com.example.boilerplate.repositories;
//
//import com.example.boilerplate.BoilerplateApplication;
//import com.example.boilerplate.models.Address;
//import com.example.boilerplate.models.Hospital;
//import com.example.boilerplate.models.User;
//import com.example.boilerplate.services.PasswordHasher;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = BoilerplateApplication.class)
//public class UserRepositoryTest {
//    @Autowired
//    private AddressRepository addressRepository;
//    @Autowired
//    private HospitalRepository hospitalRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void testSaveUser() {
//        Address address = new Address();
//        address.setCity("Burgas");
//        address.setNumber(400);
//        address.setCountry("Bulgaria");
//        address.setStreet("Zornica");
//        assertNotNull(addressRepository.save(address));
//        Hospital hospital = new Hospital();
//        hospital.setName("Deva Maria");
//        hospital.setAddress(address);
//        assertNotNull(hospitalRepository.save(hospital));
//        User user = new User();
//        user.setFirstName("Ivan");
//        user.setHospital(hospital);
//        user.setIsAdmin(false);
//        user.setLastName("Ivanov");
//        user.setUsername("Milko");
//        user.setPassword("strong password");
//        assertNotNull(userRepository.save(user));
//        Optional<User> temp = userRepository.findById(user.getId());
//        assertTrue(temp.isPresent());
//        assertEquals(temp.get().getId(), user.getId());
//        assertEquals(temp.get().getPassword(), PasswordHasher.hashPassword("strong password"));
//        userRepository.delete(user);
//        hospitalRepository.delete(hospital);
//        addressRepository.delete(address);
//
//
//    }
//}
