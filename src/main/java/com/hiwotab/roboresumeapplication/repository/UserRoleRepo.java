package com.hiwotab.roboresumeapplication.repository;


import com.hiwotab.roboresumeapplication.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepo extends CrudRepository<UserRole,Long> {
    UserRole findByUrole(String role);
}
