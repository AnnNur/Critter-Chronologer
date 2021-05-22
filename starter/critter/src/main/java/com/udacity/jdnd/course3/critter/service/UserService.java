package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.UserRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<Customer> getAllCustomers() {
        return userRepository.findAllCustomers();
    }

    public Customer getCustomerById(Long customerId) {
        return userRepository.findCustomerById(customerId);
    }

    public Employee getEmployeeById(Long employeeId) {
        return userRepository.findEmployeeById(employeeId);
    }

    public List<Employee> getAvailableEmployees(DayOfWeek day, Set<EmployeeSkill> skills) {
        List<Employee> employeesListAvailableInDay = userRepository.findAllByDaysAvailableContaining(day);
        return employeesListAvailableInDay.stream()
                .filter(availableEmployee -> availableEmployee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}

