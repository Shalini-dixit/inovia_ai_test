package com.inovia.assignment.repository;

import com.inovia.assignment.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for Employee
 *
 * @author Shalini Dixit
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
