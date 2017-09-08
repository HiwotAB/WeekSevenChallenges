package com.hiwotab.roboresumeapplication.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min=2,max=100)
    private String title;

    @NotEmpty
    @Size(min=2,max=500)
    private String employer;

    @NotEmpty
    @Size(min=2,max=500)
    private String description;

    @NotEmpty
    @Size(min=2,max=50)
    private String salary;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns=@JoinColumn(name="job_id"),
            inverseJoinColumns=@JoinColumn(name="skill_id"))

    private Collection<Skills> jobskill;

    public Job(){
        this.jobskill=new ArrayList<Skills>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Collection<Skills> getJobskill() {
        return jobskill;
    }

    public void setJobskill(Collection<Skills> jobskill) {
        this.jobskill = jobskill;
    }

    public void addSkill(Skills skills)
    {
        this.jobskill.add(skills);
    }

}
