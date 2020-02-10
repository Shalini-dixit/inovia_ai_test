package com.inovia.assignment.dao;


import com.inovia.assignment.model.Employee;
import com.inovia.assignment.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeDAOTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        employeeRepository.save(new Employee.Builder().withHireDate(Instant.now().minus(180L, ChronoUnit.DAYS)).withName("test1").withSalary(101).build());
        employeeRepository.save(new Employee.Builder().withHireDate(Instant.now().minus(120L, ChronoUnit.DAYS)).withName("test2").withSalary(102).build());
        employeeRepository.save(new Employee.Builder().withHireDate(Instant.now().minus(110L, ChronoUnit.DAYS)).withName("test3").withSalary(103).build());
        employeeRepository.save(new Employee.Builder().withHireDate(Instant.now().minus(100L, ChronoUnit.DAYS)).withName("test4").withSalary(104).build());
    }

    @Test
    public void getEmployees() {
        assertThat(employeeRepository.findAll().size(), is(4));
    }

    @Test
    public void addEmployee() {
        Employee employee = employeeRepository.save(new Employee.Builder().withHireDate(Instant.now().minus(95L, ChronoUnit.DAYS)).withName("new Emp").withSalary(5000).build());
        assertThat(employee.getId(), notNullValue());
    }

    @Test
    public void getEmployeeById() {
        Employee employee = employeeRepository.findAll().get(0);

        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        assertThat(employeeOptional.get(), notNullValue());

        assertThat(employeeOptional.get(), allOf(
                hasProperty("name", is(employee.getName())),
                hasProperty("salary", is(employee.getSalary())),
                hasProperty("hireDate", is(employee.getHireDate())),
                hasProperty("id", is(employee.getId())))
        );
    }

    @Test
    public void deleteEmployee() {
        Employee oldEmployee = employeeRepository.findAll().get(0);

        employeeRepository.deleteById(oldEmployee.getId());
        Optional<Employee> employee = employeeRepository.findById(oldEmployee.getId());

        assertThat(employee.isPresent(), is(false));
    }
}
