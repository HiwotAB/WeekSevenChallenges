package com.hiwotab.roboresumeapplication.services;



import com.hiwotab.roboresumeapplication.model.Resume;
import com.hiwotab.roboresumeapplication.model.UserRole;
import com.hiwotab.roboresumeapplication.repository.ResumeRepostory;
import com.hiwotab.roboresumeapplication.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {
    @Autowired
    ResumeRepostory resumeRepostory;
    @Autowired
    UserRoleRepo userRoleRepo;
    @Autowired
    public UserService(ResumeRepostory userRepo){
        this.resumeRepostory=userRepo;
    }
    public Resume findByEmail(String email){
        return resumeRepostory.findByEmail(email);

    }
    public Long countByEmail(String email){
        return resumeRepostory.countByEmail(email);

    }
    public Resume findByUsername(String username){
        return resumeRepostory.findByUsername(username);

    }
    public void saveRecruiters(Resume  resume){
        resume.setRoles(Arrays.asList(userRoleRepo.findByUrole("RECRUITERS")));
        resume.setEnabled(true);
        resumeRepostory.save(resume);
    }
    public void saveJobSeeker(Resume  resume){
        resume.setRoles(Arrays.asList(userRoleRepo.findByUrole("JOB SEEKERS")));
        resume.setEnabled(true);
        resumeRepostory.save(resume);
    }

    public void saveAdmin(Resume resumes){
        resumes.setRoles(Arrays.asList(userRoleRepo.findByUrole("ADMIN")));
        resumes.setEnabled(true);
        resumeRepostory.save(resumes);
    }


}
