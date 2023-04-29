package com.example.boilerplate.repositories;

import com.example.boilerplate.BoilerplateApplication;
import com.example.boilerplate.models.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BoilerplateApplication.class)
public class AddressRepositoryTest {
    @Autowired
    private AddressRepository repository;
    @Test
    public void testSaveAddress(){
        Address address=new Address();
        address.setCity("Varna");
        address.setStreet("Zornica");
        address.setCountry("Bulgaria");
        address.setNumber(400);
        assertNotNull(repository.save(address).getId());
        repository.delete(address);
    }
    @Test
    public void testSaveAddressWithCyrillic(){
        Address address=new Address();
        address.setCity("Варна");
        address.setStreet("Зорница");
        address.setCountry("България");
        address.setNumber(400);
        assertNotNull(repository.save(address).getId());
        repository.delete(address);
    }
}
