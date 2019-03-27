package com.Coffee.CoffeeShop.repositories;

import com.Coffee.CoffeeShop.models.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {}
