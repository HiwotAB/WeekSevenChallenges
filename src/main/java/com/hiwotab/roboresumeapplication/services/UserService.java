package com.hiwotab.roboresumeapplication.services;


import byAJ.Securex.models.NewUsers;
import byAJ.Securex.repositories.NewUserRepo;
import byAJ.Securex.repositories.UseRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {
    @Autowired
    NewUserRepo userRepo;
    @Autowired
    UseRoleRepo roleRepo;
    @Autowired
    public UserService(NewUserRepo userRepo){
        this.userRepo=userRepo;
    }
    public NewUsers findByEmail(String email){
        return userRepo.findByEmail(email);

    }
    public Long countByEmail(String email){
        return userRepo.countByEmail(email);

    }
    public NewUsers findByUsername(String username){
        return userRepo.findByUsername(username);

    }
    public void saveUser(NewUsers user){
        user.setRoles(Arrays.asList(roleRepo.findByUrole("USER")));
        user.setEnabled(true);
        userRepo.save(user);
    }
    public void saveAdmin(NewUsers users){
        users.setRoles(Arrays.asList(roleRepo.findByUrole("ADMIN")));
        users.setEnabled(true);
        userRepo.save(users);
    }


}
