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
import java.util.regex.Pattern;

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
    /*******************************home Page and default home page*************************************************/
    @GetMapping("/")
    public String showHomePages() {
        return "index";
    }
    @GetMapping("/homePage")
    public String showHomePage() {
        return "index";
    }

    /************************************User Info to add user to modify the existing and to delete the existing user information *********************************************************************/
/*This method is used to dispaly a form of person info for a user to enter values*/
    @GetMapping("/addUser")
    public String addUserInfo(Model model) {

        /*Here as we consider that we have only one person information so if more than one person tries
	       to enter his if then the submit button will get
	        disable so that they cannot enter more than ten information*/
        model.addAttribute("rowNumber", resumeRepostory.count());
        model.addAttribute("newUser", new Resume());
        model.addAttribute("disSubmit", resumeRepostory.count() >= 1);
        return "addUser";
    }
    /*This method is used to check the validation for each values has been entered and if it is valid data then it will save it to resume(user )table
    * also store and check  the record of the row number in data base table*/
    @PostMapping("/addUser")
    public String addUserInfo(@Valid @ModelAttribute("newUser") Resume resume, Model model,BindingResult bindingResult){
        model.addAttribute("rowNumber", resumeRepostory.count());
        if (bindingResult.hasErrors()) {
            return "addUser";
        }
        resumeRepostory.save(resume);
        return "dispUserInfo";
    }
    /*This method is used to modify the existing in forms then update data bas tables according to there modify fields */
    @RequestMapping("/updateUserInfo/{id}")
    public String updateUserInfo(@PathVariable("id") long id, Model model){
        model.addAttribute("newUser", resumeRepostory.findOne(id));
        return "addUser";
    }
    /*This method is used to delete the existing data  records from data base table and dispaly the rest of data which has been there*/
    @RequestMapping("/deleteUserInfo/{id}")
    public String delUserInfo(@PathVariable("id") long id){
        eduAchievementsRepostory.delete(id);
        return "redirect:/listUserInfo";
    }
    /*This method is used to display the existing data  records from data base table*/
    @RequestMapping("/listUserInfo")
    public String listUserInfo(Model model){
        model.addAttribute("searchUser", resumeRepostory.findAll());
        return "listUserInfo";
    }
    /******************Education Information to add education to modify the existing and to delete the existing education information  *******************************************************************/
  /*This method is used to dispaly a form of  education achievement to person to enter values*/
    @GetMapping("/addEduInfo")
    public String addEducationInfo(Model model) {
         /*Here we allow the user only has to enter 10 most recent education achivement information and
	    if the user or person tries to enter more than 10 information then submit button will get
	    disable so that they cannot enter more than ten information*/
        model.addAttribute("disSubmit", eduAchievementsRepostory.count() >= 10);
        model.addAttribute("rowNumber", eduAchievementsRepostory.count());
        model.addAttribute("newEduInfo", new EduAchievements());
        return "addEduInfo";
    }

    /*This method is used to check the validation for each values has been entered and if it is valid data then it will save it to data base table
   * also store and check  the record of the rows in data base table*/
    @PostMapping("/addEduInfo")
    public String addEducationInfo(@Valid @ModelAttribute("newEduInfo") EduAchievements eduAchievements,Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // expect at least one educational info
            model.addAttribute("rowNumber", eduAchievementsRepostory.count());
            return "addEduInfo";
        }
        eduAchievementsRepostory.save(eduAchievements);
        model.addAttribute("rowNumber", eduAchievementsRepostory.count());
        return "dispEduInfo";

    }

    /*This method is used to modify the existing in forms then update data bas tables according to there modify fields */
    @RequestMapping("/updateEduInfo/{id}")
    public String updateEduInfo(@PathVariable("id") long id, Model model){
        model.addAttribute("newEduInfo", eduAchievementsRepostory.findOne(id));
        return "addEduInfo";
    }
    /*This method is used to delete the existing data  records from data base table and dispaly the rest of data which has been there*/
    @RequestMapping("/deleteEduInfo/{id}")
    public String delEduInfo(@PathVariable("id") long id){
        eduAchievementsRepostory.delete(id);
        return "redirect:/listEduInfo";
    }
    /*This method is used to display the existing data  records from data base table*/
    @RequestMapping("/listEduInfo")
    public String listEduInfo(Model model){
        model.addAttribute("searchEdu", eduAchievementsRepostory.findAll());
        return "listEduInfo";
    }
    /***********************************************************************************************************************/
    /*********************************************Work Experiences Information to add *Work Experiences to modify the existing and to delete the existing *Work Experiences information *********************************************************/
    /*This method is used to dispaly a form of  work Experiences to person to enter values*/
    @GetMapping("/addWorkExpInfo")
    public String addWorkExpiInfo(Model model) {
         /*Here we allow the user only has to enter 10 most recent work experiences information and
	    if the user or person tries to enter more than 10 information then submit button will get
	    disable so that they cannot enter more than ten information*/
        model.addAttribute("disSubmit", workExperiencesRepostory.count() >= 10);
        model.addAttribute("rowNumber", workExperiencesRepostory.count());
        model.addAttribute("newWork", new WorkExperiences());
        return "addWorkExpInfo";
    }

    /*This method is used to check the validation for each values has been entered and if it is valid data then it will save it to data base table
    * also store and check  the record of the rows in data base table*/
    @PostMapping("/addWorkExpInfo")
    public String addWorkExpiInfo(@Valid @ModelAttribute("newWork") WorkExperiences workExperiences,Model model, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "addWorkExpInfo";
        }

        workExperiencesRepostory.save(workExperiences);
        model.addAttribute("rowNumber", workExperiencesRepostory.count());
        return "dispWorkExpInfo";
    }
    /*This method is used to modify the existing in forms then update data bas tables according to there modify fields */
    @RequestMapping("/updateExpInfo/{id}")
    public String updateWorkExp(@PathVariable("id") long id, Model model){
        model.addAttribute("newWork", workExperiencesRepostory.findOne(id));
        return "addWorkExpInfo";
    }
    /*This method is used to delete the existing data  records from data base table and dispaly the rest of data which has been there*/
    @RequestMapping("/deleteExpInfo/{id}")
    public String delWorkExpInfo(@PathVariable("id") long id){
        workExperiencesRepostory.delete(id);
        return "redirect:/listExpInfo";
    }
    /*This method is used to display the existing data  records from data base table*/
    @RequestMapping("/listExpInfo")
    public String listWorkExpInfo(Model model){
        model.addAttribute("searchExp", workExperiencesRepostory.findAll());
        return "listExpInfo";
    }
    /***************************************************************************************************************************/

    /***********************************************Skills Information to add Skills to modify the existing and to delete the existing Skills information****************************************************************************/
   /*This method is used to dispaly a form of  skills to person to enter values*/
    @GetMapping("/addSkillInfo")
    public String addSkilsInfo(Model model) {
        /*Here we allow the user only has to enter 20 skills information and
	    if the user or person tries to enter more than 20 information then submit button will get
	    disable so that they cannot enter more than ten information*/
        model.addAttribute("disSubmit", skillsRepostory.count() >= 20);
        model.addAttribute("rowNumber", skillsRepostory.count());
        model.addAttribute("newSkill", new Skills());
        return "addSkillInfo";
    }
    /*This method is used to check the validation for each values has been entered and if it is valid data then it will save it to data base table
     * also store and check  the record of the rows in data base table*/
    @PostMapping("/addSkillInfo")
    public String addSkilsInfo(@Valid @ModelAttribute("newSkill") Skills skills, Model model,BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("rowNumber", skillsRepostory.count());
            return "addSkillInfo";
        }

        skillsRepostory.save(skills);
        model.addAttribute("rowNumber", skillsRepostory.count());
        return "dispSkillsInfo";
    }

    /*This method is used to modify the existing in forms then update data bas tables according to there modify fields */
    @RequestMapping("/updateSkillInfo/{id}")
    public String updateSkillInfo(@PathVariable("id") long id, Model model){
        model.addAttribute("newSkill", skillsRepostory.findOne(id));
        return "addSkillInfo";
    }
    /*This method is used to delete the existing data  records from data base table and dispaly the rest of data which has been there*/
    @RequestMapping("/deleteSkillInfo/{id}")
    public String delSkillInfo(@PathVariable("id") long id){
        skillsRepostory.delete(id);
        return "redirect:/listSkillInfo";
    }
    /*This method is used to display the existing data  records from data base table*/
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
    @GetMapping("/SummerizedResume")
    public String summary(Model model) {
        Resume resume=resumeRepostory.findOne(new Long(1));

        ArrayList<EduAchievements> eduAchievementsArrayList=(ArrayList<EduAchievements>)eduAchievementsRepostory.findAll();
        resume.setEduAchieve(eduAchievementsArrayList);

        ArrayList<Skills>skillsArrayList=(ArrayList<Skills>)skillsRepostory.findAll();
        resume.setSkils(skillsArrayList);

        ArrayList<WorkExperiences>workExperiencesArrayList=(ArrayList<WorkExperiences>) workExperiencesRepostory.findAll();
        resume.setWorkExp(workExperiencesArrayList);

        model.addAttribute("searchUser", resume);

        return "SummerizedResume";
    }
    /**
     * Checks the param date is in 'yyyy-MM-dd' format
     * @param _date the date to be validated
     * @return returns true if _date is in 'yyyy-MM-dd' format, false otherwise
     */
    /*public static boolean validateDate(String _date) {

        String pattern = "([0-9]{4})\\-((0[1-9]|1[0-3])\\-([0-2][0-9]|30))|((13)\\-(0[0-6]))";
        return Pattern.matches(pattern, (CharSequence) _date);

    }*/

}