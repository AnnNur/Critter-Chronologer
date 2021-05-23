package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    PetService petService;
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        Schedule savedSchedule = scheduleService.addSchedule(schedule);
        return convertScheduleToScheduleDTO(savedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> allSchedules = scheduleService.getSchedules();

        return allSchedules.stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedulesForPet = scheduleService.getSchedulesForPet(petId);

        return schedulesForPet.stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedulesForEmployee = scheduleService.getSchedulesForEmployee(employeeId);

        return schedulesForEmployee.stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedulesForCustomer = scheduleService.getSchedulesForCustomer(customerId);

        return schedulesForCustomer.stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Employee> employees = new ArrayList<>();
        if (scheduleDTO.getEmployeeIds() != null) {
            employees = scheduleDTO.getEmployeeIds().stream()
                    .map(employeeId -> employeeService.getEmployeeById(employeeId))
                    .collect(Collectors.toList());
        }
        schedule.setEmployees(employees);

        List<Pet> pets = new ArrayList<>();
        if (scheduleDTO.getPetIds() != null) {
            pets = petService.getAllPetsByIds(scheduleDTO.getPetIds());
        }
        schedule.setPets(pets);
        schedule.setActivities(scheduleDTO.getActivities());

        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> employeeIds = new ArrayList<>();
        schedule.getEmployees().forEach(employee -> employeeIds.add(employee.getId()));
        scheduleDTO.setEmployeeIds(employeeIds);

        List<Long> petIds = new ArrayList<>();
        schedule.getPets().forEach(pet -> petIds.add(pet.getId()));
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }
}
