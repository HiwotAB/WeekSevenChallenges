package com.hiwotab.roboresumeapplication.repository;

import com.hiwotab.roboresumeapplication.model.Job;
import com.hiwotab.roboresumeapplication.model.Skills;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Set;

public interface JobRepository extends CrudRepository<Job,Long> {

    Iterable<Job> findAllByJobskill(Collection<Skills> skills, Collection<Skills>jobskills);
    Iterable<Job> findByEmployer(String partialString);
    Iterable<Job>findByTitle(String partitalString);
}
