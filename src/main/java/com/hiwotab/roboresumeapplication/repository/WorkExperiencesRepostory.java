package com.hiwotab.roboresumeapplication.repository;

import com.hiwotab.roboresumeapplication.model.Resume;
import com.hiwotab.roboresumeapplication.model.WorkExperiences;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Set;

public interface WorkExperiencesRepostory extends CrudRepository<WorkExperiences,Long> {
    Set<WorkExperiences> findByResume(Resume resume);
    ArrayList<WorkExperiences> findByOrgName(String string);

}