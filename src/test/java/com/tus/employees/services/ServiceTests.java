package com.tus.employees.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tus.employees.dao.EmployeeRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.tus.employees.handlers.RecordNotFoundException;
import com.tus.employees.model.Employee;
import com.tus.employees.services.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {

  @InjectMocks
  EmployeeService service;

  @Mock
  EmployeeRepository dao;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAllEmployees() {
    List<Employee> list = new ArrayList<Employee>();
    Employee empOne = new Employee("John", "John");
    Employee empTwo = new Employee("Alex", "kolenchiski");
    Employee empThree = new Employee("Steve", "Waugh");

    list.add(empOne);
    list.add(empTwo);
    list.add(empThree);

    when(dao.findAll()).thenReturn(list);

    //test
    List<Employee> empList = service.findAll();

    assertEquals(3, empList.size());
    verify(dao, times(1)).findAll();
  }

  @Test
  void testCreateOrSaveEmployee() {
    Employee employee = new Employee("Dain", "Rob");

    service.save(employee);

    verify(dao, times(1)).save(employee);
  }
  
  @Test
  void testDeleteAll() {

    service.deleteAll();

    verify(dao, times(1)).deleteAll();
  }
  
  
  @Test
  void testUpdateExistingEmployee() {
      // Arrange
      Long existingEmployeeId = 1L;
      Employee existingEmployee = new Employee("Jane", "Doe");
      Employee updatedEmployee = new Employee("Jane1", "Smith");
      when(dao.findById(Mockito.anyInt())).thenReturn(Optional.of(existingEmployee));
      when(dao.save(Mockito.any())).thenReturn(updatedEmployee);
      updatedEmployee.setId(100);
      // Act
      Employee savedEmployee = service.save(updatedEmployee);

      // Assert
      //assertEquals(updatedEmployee, savedEmployee);
      verify(dao, times(1)).findById(Mockito.anyInt());
      //verify(dao, times(1)).save(updatedEmployee);
  }

	/*
	 * @Test void testSaveWithExistingId() { // Arrange Employee newEmployee = new
	 * Employee("Jack", "Smith"); newEmployee.setId(100);
	 * when(dao.findById(Mockito.anyInt())).thenReturn(Optional.empty());
	 * service.save(newEmployee); // Act & Assert
	 * //assertThrows(RecordNotFoundException.class, () ->
	 * {service.save(newEmployee); }); verify(dao,
	 * times(1)).findById(Mockito.anyInt()); verify(dao,
	 * never()).save(Mockito.any()); }
	 */
}
