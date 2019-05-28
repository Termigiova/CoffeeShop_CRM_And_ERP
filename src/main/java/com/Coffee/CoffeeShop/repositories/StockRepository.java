package com.Coffee.CoffeeShop.repositories;

import com.Coffee.CoffeeShop.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query(
            value = "SELECT * FROM stock WHERE brand = ?1",
            nativeQuery = true)
    Collection<Stock> findAllAlpuraProductsByName(String brandName);

}
