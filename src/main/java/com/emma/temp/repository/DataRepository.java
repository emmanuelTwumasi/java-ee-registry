/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emma.temp.repository;

import com.emma.temp.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Gracias
 */
@Stateless
public class DataRepository {

    @PersistenceContext(unitName = "registry_unit")
    EntityManager em;

    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class).getResultList();
    }

    public Optional<User> getUser(String username) {
        return em.createQuery("SELECT u FROM User u WHERE u.email =:email", User.class)
                .setParameter("email", username)
                .getResultList()
                .stream()
                .findFirst();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Transactional
    public User saveOrUpdateUser(User user) {
        if (user.getId() == null) {
            em.persist(user);
            em.flush();
            return user;
        } else {
            return em.merge(user);
        }
    }
}
