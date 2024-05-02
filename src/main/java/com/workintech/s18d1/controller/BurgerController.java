package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDaoImpl;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import com.workintech.s18d1.util.BurgerValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/burgers")
@CrossOrigin("*")
public class BurgerController {

    private BurgerDaoImpl burgerDao;

    @Autowired
    public BurgerController(BurgerDaoImpl burgerDao) {
        this.burgerDao = burgerDao;
    }

    @GetMapping
    public List<Burger> getAllBurgers() {
        return burgerDao.findAll();
    }

    @GetMapping("/{id}")
    public Burger getBurgerById(@PathVariable Long id) {
        Burger burger = burgerDao.findById(id);
        BurgerValidation.validateBurger(burger);
        return burger;
    }

    @PostMapping
    public Burger createBurger(@RequestBody Burger burger) {
        BurgerValidation.validateBurger(burger);
        return burgerDao.save(burger);
    }

    @PutMapping("/{id}")
    public Burger updateBurger(@PathVariable Long id, @RequestBody Burger updatedBurger) {
        BurgerValidation.validateBurgerExists(id, burgerDao);
        BurgerValidation.validateBurger(updatedBurger);
        updatedBurger.setId(id);
        return burgerDao.update(updatedBurger);
    }

    @DeleteMapping("/{id}")
    public void deleteBurger(@PathVariable Long id) {
        BurgerValidation.validateBurgerExists(id, burgerDao);
        burgerDao.remove(id);
    }

    @GetMapping("/findByPrice")
    public List<Burger> findByPrice(@RequestParam Double price) {
        return burgerDao.findByPrice(price);
    }

    @GetMapping("/findByBreadType")
    public List<Burger> findByBreadType(@RequestParam BreadType breadType) {
        return burgerDao.findByBreadType(breadType);
    }

    @GetMapping("/findByContent")
    public List<Burger> findByContent(@RequestParam String content) {
        return burgerDao.findByContent(content);
    }
}
