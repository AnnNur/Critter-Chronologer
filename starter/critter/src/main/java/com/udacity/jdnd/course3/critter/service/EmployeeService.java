package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    public List<Employee> getAvailableEmployees(DayOfWeek day, Set<EmployeeSkill> skills) {
        List<Employee> employeesListAvailableInDay = employeeRepository.findAllByDaysAvailableContaining(day);
        return employeesListAvailableInDay.stream()
                .filter(availableEmployee -> availableEmployee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}