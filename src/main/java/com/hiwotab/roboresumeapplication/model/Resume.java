package com.hiwotab.roboresumeapplication.model;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min=2, max = 30)
    private String firstname;

    @NotEmpty
    @Size(min=2, max = 30)
    private String lastname;

    @NotEmpty
    @Email
    private String email;

    public ArrayList<EduAchievements> eduAchieve;

    private  ArrayList<WorkExperiences>  workExp;

    private ArrayList<Skills> skils;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {return firstname; }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<EduAchievements> getEduAchieve() {
        return eduAchieve;
    }
    public void setEduAchieve(ArrayList<EduAchievements> eduAchieve) {
        this.eduAchieve = eduAchieve;
    }

    public ArrayList<WorkExperiences> getWorkExp() {
        return workExp;
    }

    public void setWorkExp(ArrayList<WorkExperiences> workExp) {
        this.workExp = workExp;
    }

    public ArrayList<Skills> getSkils() {
        return skils;
    }

    public void setSkils(ArrayList<Skills> skils) {
        this.skils = skils;
    }
}
