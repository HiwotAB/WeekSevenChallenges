package com.hiwotab.roboresumeapplication.repository;

import com.hiwotab.roboresumeapplication.model.Resume;
import org.springframework.data.repository.CrudRepository;


public interface ResumeRepostory extends CrudRepository<Resume,Long> {
}

