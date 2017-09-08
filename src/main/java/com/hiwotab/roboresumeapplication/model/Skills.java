package com.hiwotab.roboresumeapplication.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;


@Entity
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min=1,max=50)
    private String skill;

    @NotEmpty
    @Size(min=1,max=30)
    private String rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="resume_id")
    private Resume resume;

    @ManyToMany(mappedBy="jobskill",fetch=FetchType.LAZY)
    private Collection<Job> jobs;

    public Skills(){
        this.jobs=new ArrayList<Job>();
    }



    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Collection<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Collection<Job> jobs) {
        this.jobs = jobs;
    }

    public void addJob(Job job)
    {
        this.jobs.add(job);
    }
}
