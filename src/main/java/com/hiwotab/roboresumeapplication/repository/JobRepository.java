package com.hiwotab.roboresumeapplication.repository;

import com.hiwotab.roboresumeapplication.model.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface JobRepository extends CrudRepository<Job,Long> {
//    Set<Job> findByResume(Resume resume);
    Iterable<Job> findAllByJobskill(String skill);

    Iterable<Job> findByEmployer(String partialString);
}
