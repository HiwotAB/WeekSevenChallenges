package com.hiwotab.roboresumeapplication.repository;



import com.hiwotab.roboresumeapplication.model.Resume;
import com.hiwotab.roboresumeapplication.model.Skills;
import com.hiwotab.roboresumeapplication.model.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SkillsRepostory extends CrudRepository<Skills,Long> {
    Set<Skills> findByResume(Resume resume);
    Skills findBySkill(String skill);
}