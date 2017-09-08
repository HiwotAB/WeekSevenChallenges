package com.hiwotab.roboresumeapplication.model;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min=2, max = 30)
    @Column(name="firs_tname")
    private String firstname;

    @NotEmpty
    @Size(min=2, max = 30)
    @Column(name="last_name")
    private String lastname;

    @NotEmpty
    @Email
    @Column(name="email",nullable = false)
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="username")
    private String username;

    @Column(name="selectVal")
    private String selectVal;

    @OneToMany(mappedBy = "resume",cascade= CascadeType.ALL,fetch=FetchType.EAGER)
    public Set<EduAchievements> eduAchievementsSet;

    @OneToMany(mappedBy = "resume",cascade= CascadeType.ALL,fetch=FetchType.EAGER)
    public Set<Skills> skillsSet;

    @OneToMany(mappedBy = "resume",cascade= CascadeType.ALL,fetch=FetchType.EAGER)
    public Set<WorkExperiences> workExperiencesSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns=@JoinColumn(name="resume_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Collection<UserRole> roles;


    //constructor for resume and  initialize an empty set of education, skill and exp
    public Resume(){
        this.eduAchievementsSet= new HashSet<EduAchievements>();
        this.skillsSet=new HashSet<Skills>();
        this.workExperiencesSet=new HashSet<WorkExperiences>();
        this.roles=new ArrayList<UserRole>();
    }

    public Set<EduAchievements> getEduAchievementsSet() {
        return eduAchievementsSet;
    }

    public void setEduAchievementsSet(Set<EduAchievements> eduAchievementsSet) {
        this.eduAchievementsSet = eduAchievementsSet;
    }

    public Set<Skills> getSkillsSet() {
        return skillsSet;
    }

    public void setSkillsSet(Set<Skills> skillsSet) {
        this.skillsSet = skillsSet;
    }

    public Set<WorkExperiences> getWorkExperiencesSet() {
        return workExperiencesSet;
    }

    public void setWorkExperiencesSet(Set<WorkExperiences> workExperiencesSet) {
        this.workExperiencesSet = workExperiencesSet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSelectVal() {
        return selectVal;
    }

    public void setSelectVal(String selectVal) {
        this.selectVal = selectVal;
    }

    public Collection<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Collection<UserRole> roles) {
        this.roles = roles;
    }

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

    public void addEduAchievements(EduAchievements eduAchievements){
        eduAchievements.setResume(this);
        this.eduAchievementsSet.add(eduAchievements);
    }
    public void addSkills(Skills skills){
        skills.setResume(this);
        this.skillsSet.add(skills);
    }
    public void addWorkExperiences(WorkExperiences workExperiences){
        workExperiences.setResume(this);
        this.workExperiencesSet.add(workExperiences);
    }
    public void addRole(UserRole role)
    {
        this.roles.add(role);
    }

}
