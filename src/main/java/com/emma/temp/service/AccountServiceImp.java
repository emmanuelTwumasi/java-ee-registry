/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emma.temp.service;

import com.emma.temp.entities.User;
import com.emma.temp.exception.CustomException;
import com.emma.temp.model.request.AccountRequest;
import com.emma.temp.model.request.PasswordReset;
import com.emma.temp.repository.DataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gracias
 */@ApplicationScoped
public class AccountServiceImp implements AccountService {
    private static final Logger logger = Logger.getLogger(AccountServiceImp.class.getName());
    @Inject
    private Pbkdf2PasswordHash passwordHasher;
    @Inject
    private DataRepository userRepository;

    @Override
    public List<User> listAllUsers() {
        logger.info("Listing all users");
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        logger.log(Level.INFO, "Getting user by ID: {0}", id);
        User user = userRepository.findById(id);
        if (user == null) {
            logger.log(Level.SEVERE, "User not found with ID: {0}", id);
            throw new CustomException("Account not found.", Response.Status.NOT_FOUND);
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUser(username).orElse(null);
    }

    @Override
    public User createUser(AccountRequest accountRequest) {
        if (userRepository.getUser(accountRequest.getEmail()).isPresent()) {
            throw new CustomException("Account with email already exists.", Response.Status.CONFLICT);
        }
        User user = new User(accountRequest.getFirstName(), accountRequest.getLastName(), accountRequest.getEmail(), passwordHasher.generate(accountRequest.getPassword().toCharArray()), accountRequest.getBirthDate());
        User account = userRepository.saveOrUpdateUser(user);
        if (account == null) {
            logger.log(Level.SEVERE, "An Error occurred whilst creating account, please try again.");
            throw new CustomException("An Error occurred whilst creating account, please try again.", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return account;
    }

    @Override
    public User updateUser(Long id, AccountRequest accountRequest) {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new CustomException("Account not found.", Response.Status.NOT_FOUND);
        }

        user.setFirstName(accountRequest.getFirstName());
        user.setLastName(accountRequest.getLastName());
        user.setEmail(accountRequest.getEmail());
        user.setBirthDate(accountRequest.getBirthDate());
        User account = userRepository.saveOrUpdateUser(user);

        if (account == null) {
            throw new CustomException("An Error occurred whilst updating account, please try again.", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return account;
    }

    @Override
    public User updateUserByUsername(String email,AccountRequest accountRequest) {
        User user = userRepository.getUser(email).orElse(null);

        if (user == null) {
            throw new CustomException("Account not found.", Response.Status.NOT_FOUND);
        }

        user.setFirstName(accountRequest.getFirstName());
        user.setLastName(accountRequest.getLastName());
        user.setEmail(accountRequest.getEmail());
        user.setBirthDate(accountRequest.getBirthDate());
        User account = userRepository.saveOrUpdateUser(user);

        if (account == null) {
            throw new CustomException("An Error occurred whilst updating account, please try again.", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return account;
    }


    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        User user = userRepository.getUser(email).orElseThrow(() -> new CustomException("Account not found.", Response.Status.NOT_FOUND));
        if (passwordHasher.verify(password.toCharArray(), user.getPassword())) {
            return user;
        }
        throw new CustomException("Invalid password", Response.Status.UNAUTHORIZED);
    }


    @Override
    public void terminateAccount(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new CustomException("Account not found.", Response.Status.NOT_FOUND);
        }
        userRepository.removeUser(user);
    }

    @Override
    public void resetPassword(PasswordReset passwordReset) {
        User user = userRepository.getUser(passwordReset.getEmail()).orElseThrow(() -> new CustomException("Account not found.", Response.Status.NOT_FOUND));
        user.setPassword(passwordHasher.generate(passwordReset.getPassword().toCharArray()));
        userRepository.saveOrUpdateUser(user);
    }
}
