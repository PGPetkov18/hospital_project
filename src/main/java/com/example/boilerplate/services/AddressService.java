package com.example.boilerplate.services;

import com.example.boilerplate.models.Address;
import com.example.boilerplate.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address saveAddress(Address address){
        return addressRepository.save(address);
    }
    public void deleteAddress(Address address){
        addressRepository.delete(address);
    }
    public Address findById(String id){
        return addressRepository.findById(id).orElse(null);
    }
    public Iterable<Address> findAll(){
        return addressRepository.findAll();
    }
}
