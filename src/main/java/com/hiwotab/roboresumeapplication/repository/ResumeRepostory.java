package com.hiwotab.roboresumeapplication.repository;

import com.hiwotab.roboresumeapplication.model.Resume;
import org.springframework.data.repository.CrudRepository;


public interface ResumeRepostory extends CrudRepository<Resume,Long> {
    Resume findByUsername(String username);
    Resume findByEmail(String email);
    Long countByEmail(String email);
    Long countByUsername(String username);
    Iterable<Resume>findByFirstname(String partialString);
}

