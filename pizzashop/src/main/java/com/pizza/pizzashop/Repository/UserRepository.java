package com.pizza.pizzashop.Repository;

import com.pizza.pizzashop.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}