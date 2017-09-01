package com.hiwotab.roboresumeapplication.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class WorkExperiences {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String orgName;
    private String profName;
    private String ProfLevel;
    private String duty;
    @DateTimeFormat(pattern ="MMM,yyyy")
    private Date startDate;
    @DateTimeFormat(pattern ="MMM,yyyy")
    private Date endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="resume_id")
    private Resume resume;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public String getProfLevel() {
        return ProfLevel;
    }

    public void setProfLevel(String profLevel) {
        ProfLevel = profLevel;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
