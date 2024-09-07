/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emma.temp.service;

import com.emma.temp.entities.User;
import com.emma.temp.repository.DataRepository;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.List;

/**
 *
 * @author Gracias
 */
@ApplicationScoped
public class RegistryService {  

    @Inject
    private DataRepository userRepository;
    
    @Inject
    Pbkdf2PasswordHash passwordHasher;


    public List<User> listAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUser(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.saveOrUpdateUser(user);
    }

    public User updateUser(User user) {
        return userRepository.saveOrUpdateUser(user);
    }  

}
