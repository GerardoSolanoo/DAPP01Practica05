package org.uv.dapp01practica05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.uv.dapp01practica05.Employee.Repositories.EmployeeRepository;
import org.uv.dapp01practica05.Employee.Entities.Employee;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger logger = Logger.getLogger(EmployeeController.class.getName());

    @GetMapping()
    public List<Employee> employeeList(){
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Employee> create(@RequestBody Employee employee){
        employeeRepository.save(employee);
        logger.info("Employee created: " + employee.toString());
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if(optionalEmployee.isEmpty())
            return ResponseEntity.notFound().build();

        employee.setKey(id);

        employeeRepository.save(employee);
        logger.info("Employee updated: " + employee.toString());
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if(optionalEmployee.isEmpty())
            return ResponseEntity.notFound().build();

        employeeRepository.deleteById(id);
        logger.info("Employee deleted with ID: " + id);
        return ResponseEntity.ok().build();
    }
}