package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import com.workintech.s18d1.util.BurgerValidation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@NoArgsConstructor
public class BurgerDaoImpl implements BurgerDao {


    private EntityManager entityManager;

    @Autowired
    public BurgerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Burger save(Burger burger) {
        entityManager.persist(burger);
        return burger;
    }

    @Override
    public Burger findById(Long id) {
        Burger burger = entityManager.find(Burger.class, id);
        if (burger == null) {
            throw new BurgerException("Burger not found", HttpStatus.NOT_FOUND);
        }
        return burger;
    }

    @Override
    public List<Burger> findAll() {
        String query = "SELECT b FROM Burger b";
        TypedQuery<Burger> typedQuery = entityManager.createQuery(query, Burger.class);
        return typedQuery.getResultList();
    }

    @Override
    public List<Burger> findByPrice(double price) {
        String query = "SELECT b FROM Burger b WHERE b.price > :price ORDER BY b.price DESC";
        TypedQuery<Burger> typedQuery = entityManager.createQuery(query, Burger.class);
        typedQuery.setParameter("price", price);
        return typedQuery.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        String query = "SELECT b FROM Burger b WHERE b.breadType = :breadType ORDER BY b.name ASC";
        TypedQuery<Burger> typedQuery = entityManager.createQuery(query, Burger.class);
        typedQuery.setParameter("breadType", breadType);
        return typedQuery.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {
        String query = "SELECT b FROM Burger b JOIN b.contents c WHERE c ILIKE :content";
        TypedQuery<Burger> typedQuery = entityManager.createQuery(query, Burger.class);
        typedQuery.setParameter("contents", content);
        return typedQuery.getResultList();
    }

    @Transactional
    @Override
    public Burger update(Burger burger) {
        return entityManager.merge(burger);
    }

    @Transactional
    @Override
    public Burger remove(Long id) {

        Burger burger = findById(id);
        if (burger != null) {
            entityManager.remove(burger);
        }

        return burger;
    }
}
