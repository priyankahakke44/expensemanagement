package com.zidiogroup9.expensemanagement.repositories;

import com.zidiogroup9.expensemanagement.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DepartmentRepository extends JpaRepository<Department,String> {
    List<Department> findByName(String name);
}
