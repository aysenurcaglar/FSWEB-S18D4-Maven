package com.workintech.s18d1.util;

import com.workintech.s18d1.dao.BurgerDaoImpl;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import org.springframework.http.HttpStatus;

public class BurgerValidation {

    public static void validateBurger(Burger burger) {
        if (burger.getName() == null || burger.getName().isEmpty()) {
            throw new BurgerException("Burger name must not be empty", HttpStatus.BAD_REQUEST);
        }

        Double price = burger.getPrice();

        if (price == null || price <= 0) {
            throw new BurgerException("Burger price must be a positive number", HttpStatus.BAD_REQUEST);
        }

        if (burger.getBreadType() == null) {
            throw new BurgerException("Bread type must not be empty", HttpStatus.BAD_REQUEST);
        }

        if (burger.getContents() == null || burger.getContents().isEmpty()) {
            throw new BurgerException("Contents must not be empty", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateBurgerExists(Long id, BurgerDaoImpl burgerDao) {
        Burger burger = burgerDao.findById(id);
        if (burger == null) {
            throw new BurgerException("Burger not found", HttpStatus.NOT_FOUND);
        }
    }
}
