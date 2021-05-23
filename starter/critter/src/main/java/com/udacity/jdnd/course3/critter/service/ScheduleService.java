package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PetRepository petRepository;

    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesForPet(Long petId) {
        Optional<Pet> petOptional = petRepository.findById(petId);
        Pet pet = petOptional.orElse(null);

        return scheduleRepository.getSchedulesByPetsContains(pet);
    }

    public List<Schedule> getSchedulesForEmployee(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Employee employee = optionalEmployee.orElse(null);

        return scheduleRepository.getSchedulesByEmployeesContains(employee);
    }

    public List<Schedule> getSchedulesForCustomer(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Customer customer = optionalCustomer.orElse(null);

        List<Schedule> schedules = new ArrayList<>();
        List<Pet> pets = customer.getPets();
        pets.stream()
                .map(pet -> scheduleRepository.getSchedulesByPetsContains(pet))
                .forEach(schedules::addAll);
        return schedules;
    }
}
