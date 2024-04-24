package org.uv.dapp01practica05.Employee.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.dapp01practica05.Employee.Entities.Employee;

public interface EmployeeRepository extends JpaRepository <Employee, Long> {
}