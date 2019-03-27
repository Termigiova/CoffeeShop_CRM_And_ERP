package com.Coffee.CoffeeShop.repositories;

import com.Coffee.CoffeeShop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
