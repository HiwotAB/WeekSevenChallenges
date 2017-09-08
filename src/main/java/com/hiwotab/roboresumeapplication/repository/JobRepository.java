package com.hiwotab.roboresumeapplication.repository;

import com.hiwotab.roboresumeapplication.model.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job,Long> {
}
