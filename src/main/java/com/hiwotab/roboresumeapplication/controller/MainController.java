package com.hiwotab.roboresumeapplication.controller;

import com.hiwotab.roboresumeapplication.model.EduAchievements;
import com.hiwotab.roboresumeapplication.model.Resume;
import com.hiwotab.roboresumeapplication.model.Skills;
import com.hiwotab.roboresumeapplication.model.WorkExperiences;
import com.hiwotab.roboresumeapplication.repository.EduAchievementsRepostory;
import com.hiwotab.roboresumeapplication.repository.ResumeRepostory;
import com.hiwotab.roboresumeapplication.repository.SkillsRepostory;
import com.hiwotab.roboresumeapplication.repository.WorkExperiencesRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class MainController {

    @Autowired
    ResumeRepostory resumeRepostory;
    @Autowired
    EduAchievementsRepostory eduAchievementsRepostory;
    @Autowired
    WorkExperiencesRepostory workExperiencesRepostory;
    @Autowired
    SkillsRepostory skillsRepostory;
    @GetMapping("/")
    public String showHomePages() {
        return "index";
    }
    @GetMapping("/homePage")
    public String showHomePage() {
        return "index";
    }

/************************************User Info**********************************************************************/
    @GetMapping("/addUser")
    public String addUserInfo(Model model) {
        model.addAttribute("newUser", new Resume());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUserInfo(@Valid @ModelAttribute("newUser") Resume resume, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "addUser";
        }

        resumeRepostory.save(resume);
        return "dispUserInfo";
    }
    @RequestMapping("/updateUserInfo/{id}")
    public String updateUserInfo(@PathVariable("id") long id, Model model){
        model.addAttribute("newUser", resumeRepostory.findOne(id));
        return "addUser";
    }

    @RequestMapping("/deleteUserInfo/{id}")
    public String delUserInfo(@PathVariable("id") long id){
        eduAchievementsRepostory.delete(id);
        return "redirect:/listUserInfo";
    }
    @RequestMapping("/listUserInfo")
    public String listUserInfo(Model model){
        model.addAttribute("searchUser", resumeRepostory.findAll());
        return "listUserInfo";
    }
/******************Education Information *******************************************************************/
    @GetMapping("/addEduInfo")
    public String addEducationInfo(Model model) {
        model.addAttribute("newEduInfo", new EduAchievements());
        return "addEduInfo";
    }
    @PostMapping("/addEduInfo")
    public String addEducationInfo(@Valid @ModelAttribute("newEduInfo") EduAchievements eduAchievements, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addEduInfo";
        }

        eduAchievementsRepostory.save(eduAchievements);
        return "dispEduInfo";
    }
    @RequestMapping("/updateEduInfo/{id}")
    public String updateEduInfo(@PathVariable("id") long id, Model model){
        model.addAttribute("newEduInfo", eduAchievementsRepostory.findOne(id));
        return "addEduInfo";
    }

    @RequestMapping("/deleteEduInfo/{id}")
    public String delEduInfo(@PathVariable("id") long id){
        eduAchievementsRepostory.delete(id);
        return "redirect:/listEduInfo";
    }
    @RequestMapping("/listEduInfo")
    public String listEduInfo(Model model){
        model.addAttribute("searchEdu", eduAchievementsRepostory.findAll());
        return "listEduInfo";
    }
    /***********************************************************************************************************************/
    /*********************************************Work Experiences*********************************************************/
    @GetMapping("/addWorkExpInfo")
    public String addWorkExpiInfo(Model model) {
        model.addAttribute("newWork", new WorkExperiences());
        return "addWorkExpInfo";
    }
    @PostMapping("/addWorkExpInfo")
    public String addWorkExpiInfo(@Valid @ModelAttribute("newWork") WorkExperiences workExperiences, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addWorkExpInfo";
        }

        workExperiencesRepostory.save(workExperiences);
        return "dispWorkExpInfo";
    }
    @RequestMapping("/updateExpInfo/{id}")
    public String updateWorkExp(@PathVariable("id") long id, Model model){
        model.addAttribute("newWork", workExperiencesRepostory.findOne(id));
        return "addWorkExpInfo";
    }

    @RequestMapping("/deleteExpInfo/{id}")
    public String delWorkExpInfo(@PathVariable("id") long id){
        workExperiencesRepostory.delete(id);
        return "redirect:/listExpInfo";
    }
    @RequestMapping("/listExpInfo")
    public String listWorkExpInfo(Model model){
        model.addAttribute("searchExp", workExperiencesRepostory.findAll());
        return "listExpInfo";
    }
    /***************************************************************************************************************************/

    /********************************************Skills*****************************************************************************/
    @GetMapping("/addSkillInfo")
    public String addSkilsInfo(Model model) {
        model.addAttribute("newSkill", new Skills());
        return "addSkillInfo";
    }
    @PostMapping("/addSkillInfo")
    public String addSkilsInfo(@Valid @ModelAttribute("newSkill") Skills skills, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addSkillInfo";
        }

        skillsRepostory.save(skills);
        return "dispSkillsInfo";
    }
    @RequestMapping("/updateSkillInfo/{id}")
    public String updateSkillInfo(@PathVariable("id") long id, Model model){
        model.addAttribute("newSkill", skillsRepostory.findOne(id));
        return "addSkillInfo";
    }

    @RequestMapping("/deleteSkillInfo/{id}")
    public String delSkillInfo(@PathVariable("id") long id){
        skillsRepostory.delete(id);
        return "redirect:/listSkillInfo";
    }
    @RequestMapping("/listSkillInfo")
    public String listSkillInfo(Model model){
        model.addAttribute("searchSkill", skillsRepostory.findAll());
        return "listSkillInfo";
    }
    /***************************************************************************************************/
   /*******************************Result Info***************************************************************/
    @GetMapping("/ResultResumeInfo")
    public String listAllResumeInfo(Model model) {
        Resume resume=resumeRepostory.findOne(new Long(1));

        ArrayList<EduAchievements> eduAchievementsArrayList=(ArrayList<EduAchievements>)eduAchievementsRepostory.findAll();
        resume.setEduAchieve(eduAchievementsArrayList);

        ArrayList<Skills>skillsArrayList=(ArrayList<Skills>)skillsRepostory.findAll();
        resume.setSkils(skillsArrayList);

        ArrayList<WorkExperiences>workExperiencesArrayList=(ArrayList<WorkExperiences>) workExperiencesRepostory.findAll();
        resume.setWorkExp(workExperiencesArrayList);

        model.addAttribute("searchUser", resume);
        //model.addAttribute("searchEdu",  eduAchievementsArrayList);
        //model.addAttribute("searchSkil", skillsArrayList);
        // model.addAttribute("searchWork", workExperiencesArrayList);
        return "ResultResumeInfo";

    }
    /*******************************************************************************************/



}