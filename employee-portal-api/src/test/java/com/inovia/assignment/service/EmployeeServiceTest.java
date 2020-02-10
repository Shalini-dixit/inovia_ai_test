package com.inovia.assignment.service;

import com.inovia.assignment.exception.RecordNotFoundException;
import com.inovia.assignment.model.Employee;
import com.inovia.assignment.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void getAllEmployees() throws Exception {

        Instant date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-31").toInstant();
        Instant date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2017-11-15").toInstant();

        List<Employee> empList = List.of(new Employee.Builder().withId(1l).withName("test1").withHireDate(date1).withSalary(1001).build(),
                new Employee.Builder().withId(2l).withName("test2").withHireDate(date2).withSalary(1002).build());

        doReturn(empList).when(employeeRepository).findAll();
        List<Employee> allEmployees = employeeService.getAllEmployees();

        assertThat(allEmployees, hasItems(
                allOf(
                        hasProperty("name", is("test1")),
                        hasProperty("salary", is(1001))
                ),
                allOf(
                        hasProperty("name", is("test2")),
                        hasProperty("salary", is(1002))
                )
        ));
    }


    @Test(expected = RecordNotFoundException.class)
    public void getEmployeeByIdExceptionTest() throws RecordNotFoundException {
        doReturn(Optional.empty()).when(employeeRepository).findById(anyLong());
        employeeService.getEmployeeById(1L);
    }

    @Test
    public void getEmployeeById() throws RecordNotFoundException {

        doReturn(Optional.of(new Employee.Builder().withId(5l).withName("test123").withHireDate(Instant.now()).withSalary(500000).build())).when(employeeRepository).findById(anyLong());
        Employee employee = employeeService.getEmployeeById(5l);

        assertThat(employee, allOf(
                hasProperty("name", is("test123")),
                hasProperty("salary", is(500000)),
                hasProperty("id", is(5l)))
        );
    }


}
