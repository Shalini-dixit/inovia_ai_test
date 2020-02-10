package com.inovia.assignment.rest;

import com.inovia.assignment.exception.RecordNotFoundException;
import com.inovia.assignment.model.Employee;
import com.inovia.assignment.service.EmployeeService;
import com.inovia.assignment.web.EmployeeController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;


    List<Employee> mockEmployeeList = Collections.emptyList();


    @Before
    public void setup() throws Exception {
        Instant date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-31").toInstant();
        Instant date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2017-11-15").toInstant();
        Instant date3 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-08-01").toInstant();

        mockEmployeeList = List.of(new Employee.Builder().withId(1l).withName("test1").withHireDate(date1).withSalary(1001).build(),
                new Employee.Builder().withId(2l).withName("test2").withHireDate(date2).withSalary(1002).build(),
                new Employee.Builder().withId(3l).withName("test3").withHireDate(date3).withSalary(1003).build());
    }

    @Test
    public void retrieveAllEmployee() throws Exception {
        doReturn(mockEmployeeList).when(employeeService).getAllEmployees();

        String expected = "[{\"id\":1,\"name\":\"test1\",\"salary\":1001,\"hireDate\":\"2016-12-30T23:00:00Z\"},{\"id\":2,\"name\":\"test2\",\"salary\":1002,\"hireDate\":\"2017-11-14T23:00:00Z\"},{\"id\":3,\"name\":\"test3\",\"salary\":1003,\"hireDate\":\"2019-07-31T22:00:00Z\"}]";

        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void getEmployeeById() throws Exception {
        Instant hireDate = new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-31").toInstant();
        doReturn(new Employee.Builder().withId(5l).withName("test123").withHireDate(hireDate).withSalary(500000).build()).when(employeeService).getEmployeeById(anyLong());

        String expected = "{\"id\":5,\"name\":\"test123\",\"salary\":500000,\"hireDate\":\"2016-12-30T23:00:00Z\"}";

        mockMvc.perform(get("/employees/5"))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void getEmployeeByIdNotFound() throws Exception {
        doThrow(new RecordNotFoundException("No employee record exist for given id :-43")).when(employeeService).getEmployeeById(any());
        mockMvc.perform(get("/employees/43"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addEmployee() throws Exception {

        String payLoad = "{\n" +
                "    \"name\": \"TestAdd\",\n" +
                "    \"salary\": 90002121,\n" +
                "    \"hireDate\": \"2020-07-30T22:00:00Z\"\n" +
                "}";

        mockMvc.perform(post("/employees").contentType(APPLICATION_JSON).content(payLoad))
                .andDo(print())
                .andExpect(status().isOk());

    }


}
