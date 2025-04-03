package com.zidiogroup9.expensemanagement.repositories;

import com.zidiogroup9.expensemanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
}
