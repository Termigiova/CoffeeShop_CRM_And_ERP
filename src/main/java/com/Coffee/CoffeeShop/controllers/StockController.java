package com.Coffee.CoffeeShop.controllers;

import com.Coffee.CoffeeShop.models.Stock;
import com.Coffee.CoffeeShop.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StockController {

    @Autowired
    StockRepository stockRepository;

    @GetMapping("/stocks")
    public String getAllStocks(Model model) {
        model.addAttribute("stocks", stockRepository.findAll());
        return "stocks/showAll";
    }

    @GetMapping("/stocks/add")
    public String showStockForm(Stock stock) { return "stocks/add"; }

    @PostMapping("/stocks")
    public String addStockItem(@Valid Stock stock, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "stocks/add";
        }

        stockRepository.save(stock);
        return "redirect:/stocks";
    }

    @GetMapping("/stocks/{id}")
    public String getStock(@PathVariable("id") long id, Model model) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() ->new IllegalArgumentException("Invalid stocks id: " + id));

        model.addAttribute("stock", stock);
        return "stocks/update";
    }

    @PostMapping("/stocks/{id}")
    public String updateStock(@PathVariable("id") long id, @Valid Stock stock,
                                     BindingResult result) {
        if (result.hasErrors()) {
            stock.setStock_id(id);
            return "redirect:/stocks/" + id;
        }

        stock.setStock_id(id);
        stockRepository.save(stock);
        return "redirect:/stocks";
    }

    @DeleteMapping("/stocks/{id}")
    public String deleteStock(@PathVariable("id") long id, Model model) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid stock Id:" + id));

        stockRepository.delete(stock);
        model.addAttribute("stocks", stockRepository.findAll());
        return "stocks/showAll";
    }

    @GetMapping("/stocks/brand/{brandName}")
    public Map<String, Double> getItemsByBrandName(@PathVariable("brandName") String brandName, Model model) {
        Collection<Stock> stockCollection = stockRepository.findAllAlpuraProductsByName(brandName);
        Map<String, Double> stockMap = new HashMap<>();
        stockCollection.forEach((stock) -> {
            stockMap.put(stock.getBrand(), stock.getPrice());
        });
        return stockMap;

//        model.addAttribute("stocks", stockCollection);
//        return "stocks/showAll";
    }

}
