package com.hiwotab.roboresumeapplication.repository;



import com.hiwotab.roboresumeapplication.model.EduAchievements;
import com.hiwotab.roboresumeapplication.model.Resume;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface EduAchievementsRepostory extends CrudRepository<EduAchievements,Long> {
    Set<EduAchievements> findByResume(Resume resume);
}