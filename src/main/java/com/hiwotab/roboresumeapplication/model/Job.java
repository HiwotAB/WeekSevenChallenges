package com.hiwotab.roboresumeapplication.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min=2,max=100)
    private String title;

    @NotEmpty
    @Size(min=2,max=50)
    private String employer;

    @NotEmpty
    @Size(min=2,max=50)
    private String description;

    @NotEmpty
    @Size(min=2,max=50)
    private String salary;
}
