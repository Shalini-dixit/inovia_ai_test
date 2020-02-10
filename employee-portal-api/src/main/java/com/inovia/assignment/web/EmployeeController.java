package com.inovia.assignment.web;


import com.inovia.assignment.exception.RecordNotFoundException;
import com.inovia.assignment.model.Employee;
import com.inovia.assignment.service.EmployeeService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class contains all rest services responsible for all Employee CRUD operations.
 *
 * @author Shalini Dixit
 */
@RestController
@RequestMapping("/employees")
@Api(value = "Employee Portal api use to fetch employee list and details")
public class EmployeeController {

    public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    @ApiOperation(value = "View list of all employees")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returned All employees", response = Employee.class, responseContainer = "List")
    })
    public ResponseEntity<List<Employee>> getAllEmployees() {
        LOGGER.info("Fetching all Employees");
        List<Employee> list = employeeService.getAllEmployees();

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "View details of a particular employee", response = Employee.class, responseContainer = "Employee Object")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returned employee", response = Employee.class),
            @ApiResponse(code = 404, message = "No employee record exist for given id")
    })
    public ResponseEntity<Employee> getEmployeeById(@ApiParam(value = "Employeee id", required = true) @PathVariable("id") Long id) throws RecordNotFoundException {
        LOGGER.info("Fetching details for employee: {}", id);
        Employee entity = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "This will create new employee record.If Employee record already exists then record will be updated")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Employee Object Created/Updated", response = Employee.class, responseContainer = "Employee Object")
    })
    public ResponseEntity<Employee> createOrUpdateEmployee(@RequestBody Employee employeeObj) {
        LOGGER.info("creating/udpating employee: {}", employeeObj);
        Employee employee = employeeService.createOrUpdateEmployee(employeeObj);
        return new ResponseEntity<>(employee, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "This will delete the employee for specified id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Employee Object Deleted"),
            @ApiResponse(code = 404, message = "No employee record exist for given id")
    })
    public ResponseEntity deleteEmployee(@ApiParam(value = "Employee Id", required = true) @PathVariable(value = "id") Long id) throws RecordNotFoundException {
        LOGGER.info("Deleting employee {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}