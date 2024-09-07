/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emma.temp.repository;

import com.emma.temp.entities.User;
import com.emma.temp.service.RegistryService;
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
    private RegistryService dataService;
    
    public void execute(@Observes @Initialized(ApplicationScoped.class) Object event) {
 
        if (dataService.listAllUsers().isEmpty()) {
            User sally = dataService.createUser(new User("Manu Michael", "Manu", "anything", "something", LocalDate.now()));
            User jhn = dataService.createUser(new User("John", "Yaw", "gohome", "kivo", LocalDate.now()));
 
//            dataService.createQuality("Wonderful", sally);
//            dataService.createQuality("Team Player", sally);
//            dataService.createQuality("Good Judgement", sally);
//            dataService.createQuality("Good Leader", sally);
// 
//            dataService.createQuality("Responsible", tom);
//            dataService.createQuality("Deligent", tom);
//            dataService.createQuality("Care fpor his teammates.", tom);
 
        }
    }
 
}