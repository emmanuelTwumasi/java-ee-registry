/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emma.temp.repository;

import com.emma.temp.entities.User;
import com.emma.temp.model.request.AccountRequest;
import com.emma.temp.service.AccountService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.time.LocalDate;

/**
 *
 * @author emma
 */
@ApplicationScoped
public class DataInitializer {

    @Inject
    private AccountService dataService;

    public void execute(@Observes @Initialized(ApplicationScoped.class) Object event) {

        if (dataService.listAllUsers().isEmpty()) {
            dataService.createUser(new AccountRequest("Manu Michael", "Manu", "anything@mail.com", "something", LocalDate.now()));
            dataService.createUser(new AccountRequest("John", "Yaw", "gohome@mail.com", "kivo", LocalDate.now()));
        }
    }


}
