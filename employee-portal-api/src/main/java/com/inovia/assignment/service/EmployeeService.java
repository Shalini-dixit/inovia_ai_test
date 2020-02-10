package com.inovia.assignment.service;


import com.inovia.assignment.exception.RecordNotFoundException;
import com.inovia.assignment.model.Employee;
import com.inovia.assignment.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class to serve all operations about employee.
 *
 * @author Shalini Dixit
 */
@Service
public class EmployeeService {

    public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    EmployeeRepository repository;

    /**
     * This method fetch all employees from DB
     *
     * @return
     */
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = repository.findAll();

        if (employeeList.size() > 0) {
            return employeeList;
        } else {
            return new ArrayList<Employee>();
        }
    }

    /**
     * This method will return details of a employee for given employee id
     *
     * @param id
     * @return
     * @throws RecordNotFoundException if related ID customer is not present in our database.
     */
    public Employee getEmployeeById(Long id) throws RecordNotFoundException {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException("No employee record exist for given id :-" + id));
    }

    /**
     * This method will create or update employee details.
     *
     * @param entity an Employee Object
     * @return an created/updated employee object
     * @throws RecordNotFoundException if related ID customer is not present in our database.
     */
    public Employee createOrUpdateEmployee(Employee entity) {

        Optional<Employee> employee = Optional.empty();
        if (entity.getId() != null) {
            employee = repository.findById(entity.getId());
        }
        if (employee.isPresent()) {
            LOGGER.info("Employee with id: {} already presdent in database. hence updating existing record", entity.getId());
            Employee newEntity = employee.get();
            newEntity.setHireDate(entity.getHireDate());
            newEntity.setName(entity.getName());
            newEntity.setSalary(entity.getSalary());
        }
        return repository.save(entity);
    }

    /**
     * This method will create or update employee details.
     *
     * @param id employee id
     * @return
     * @throws RecordNotFoundException if related ID customer is not present in our database.
     */
    public void deleteEmployee(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = repository.findById(id);

        if (employee.isPresent()) {
            repository.deleteById(id);
        } else {
            LOGGER.error("Id: {} provided for deleting customer is not present in database", id);
            throw new RecordNotFoundException("No employee record exist for given id:-" + id);
        }
    }


}