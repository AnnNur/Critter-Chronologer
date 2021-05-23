package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Pet addPet(Pet pet) {
        Pet addedPet = petRepository.save(pet);
        Customer customer = addedPet.getOwner();
        List<Pet> customerPets = Optional.ofNullable(customer.getPets()).orElse(new ArrayList<>());

        customerPets.add(addedPet);
        customer.setPets(customerPets);
        customerRepository.save(customer);

        return addedPet;
    }

    public Pet getPetById(Long petId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        return optionalPet.orElse(null);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getAllPetsByIds(List<Long> petsIds) {
        return petRepository.findAllById(petsIds);
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }
}

