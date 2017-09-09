package com.hiwotab.roboresumeapplication.controller;

import com.hiwotab.roboresumeapplication.model.*;
import com.hiwotab.roboresumeapplication.repository.*;
import com.hiwotab.roboresumeapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class MainController {

    @Autowired
    UserRoleRepo userRoleRepo;
    @Autowired
    private UserService userService;
    @Autowired
    ResumeRepostory resumeRepostory;
    @Autowired
    EduAchievementsRepostory eduAchievementsRepostory;
    @Autowired
    WorkExperiencesRepostory workExperiencesRepostory;
    @Autowired
    SkillsRepostory skillsRepostory;
    @Autowired
    JobRepository jobRepository;


    /*******************************home Page , default home page and Login***********************************************/

    @RequestMapping("/")
    public String showHomePages(Model model) {

       if(userRoleRepo.count()==0){
           UserRole userRole=new UserRole();
           userRole.setUrole("ADMIN");
           userRoleRepo.save(userRole);
           UserRole userRoleR=new UserRole();
           userRoleR.setUrole("RECRUITERS");
           userRoleRepo.save(userRoleR);
           UserRole userRoleJ=new UserRole();
           userRoleJ.setUrole("JOB SEEKERS");
           userRoleRepo.save(userRoleJ);
       }

        model.addAttribute("rowNumberP",resumeRepostory.count());
        model.addAttribute("rowNumberE",eduAchievementsRepostory.count());
        model.addAttribute("rowNumberS",skillsRepostory.count());
        model.addAttribute("rowNumberX",workExperiencesRepostory.count());

        model.addAttribute("listRoles",userRoleRepo.findAll());
        model.addAttribute("allUser", resumeRepostory.findAll());
        model.addAttribute("searchEdu", eduAchievementsRepostory.findAll());
        model.addAttribute("searchExp", workExperiencesRepostory.findAll());
        model.addAttribute("searchSkill", skillsRepostory.findAll());
        return "homePage";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String showHomePage() {
        return "index";
    }

    /************************************User Info to add user to modify the existing and to delete the existing user information *********************************************************************/
/*This method is used to dispaly a form of person info for a user to enter values*/
    @GetMapping("/signUpForm")
    public String addUserInfo(Model model) {
        model.addAttribute("newUser", new Resume());
        model.addAttribute("listRoles", userRoleRepo.findAll());
        return "signUpForm";
    }
    /*This method is used to check the validation for each values has been entered and if it is valid data then it will save it to resume(user )table
    * also store and check  the record of the row number in data base table*/
    @PostMapping("/signUpForm")
    public String addUserInfo(@Valid @ModelAttribute("newUser") Resume resume, BindingResult bindingResult,Model model){
        model.addAttribute("newUser",resume);
        if (bindingResult.hasErrors()) {

            return "signUpForm";
        }
        else if(resume.getSelectVal().equalsIgnoreCase("JOB SEEKERS")  )      {

            userService.saveJobSeeker(resume);
            model.addAttribute("message","User Account Successfully Created");
        }
        else if(resume.getSelectVal().equalsIgnoreCase("RECRUITERS"))        {

            userService.saveRecruiters(resume);
            model.addAttribute("message","User Account Successfully Created");
        }
        else
        {
            userService.saveAdmin(resume);
            model.addAttribute("message","Admin Account Successfully Created");
        }

        return "redirect:/login";
    }


    /*This method is used to modify the existing in forms then update data bas tables according to there modify fields */
    @RequestMapping("/updateUserInfo")
    public String updateUserInfo(Principal principal, Model model){
        model.addAttribute("newUser", resumeRepostory.findByUsername(principal.getName()));
        return "signUpForm";
    }
    /*This method is used to delete the existing data  records from data base table and dispaly the rest of data which has been there*/
    @RequestMapping("/deleteUserInfo/{id}")
    public String delUserInfo(@PathVariable("id") long id){
        resumeRepostory.delete(id);
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
    public String addEducationInfo(EduAchievements eduAchievements, Principal principal,Model model) {
         /*Here we allow the user only has to enter 10 most recent education achivement information and
	    if the user or person tries to enter more than 10 information then submit button will get
	    disable so that they cannot enter more than ten information*/
        model.addAttribute("disSubmit", eduAchievementsRepostory.count() >= 10);
        model.addAttribute("rowNumber", eduAchievementsRepostory.count());
        Resume resume=resumeRepostory.findByUsername(principal.getName());
        eduAchievements.setResume(resume);
        model.addAttribute("newEduInfo", eduAchievements);
        return "addEduInfo";
    }

    /*This method is used to check the validation for each values has been entered and if it is valid data then it will save it to data base table
   * also store and check  the record of the rows in data base table*/
    @PostMapping("/addEduInfo")
    public String addEducationInfo(@Valid @ModelAttribute("newEduInfo") EduAchievements eduAchievements,BindingResult bindingResult,Model model) {

        if (bindingResult.hasErrors()) {
            // expect at least one educational info
            model.addAttribute("rowNumber", eduAchievementsRepostory.count());
            return "addEduInfo";
        }

        eduAchievementsRepostory.save(eduAchievements);
        model.addAttribute("rowNumber", eduAchievementsRepostory.count());
        return "redirect:/index";

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
    public String addWorkExpiInfo(WorkExperiences workExperiences,Principal principal,Model model) {
          /*Here we allow the user only has to enter 10 most recent work experience information and
	    if the user or person tries to enter more than 10 information then submit button will get
	    disable so that they cannot enter more than ten information*/

        Resume resume=resumeRepostory.findByUsername(principal.getName());
        workExperiences.setResume(resume);
        model.addAttribute("disSubmit", workExperiencesRepostory.count() >= 10);
        model.addAttribute("rowNumber", workExperiencesRepostory.count());
        model.addAttribute("newWork",workExperiences);
        return "addWorkExpInfo";
    }

    /*This method is used to check the validation for each values has been entered and if it is valid data then it will save it to data base table
    * also store and check  the record of the rows in data base table*/
    @PostMapping("/addWorkExpInfo")
    public String addWorkExpiInfo(@Valid @ModelAttribute("newWork") WorkExperiences workExperiences,BindingResult bindingResult,Model model) {

        if (bindingResult.hasErrors()) {
            return "addWorkExpInfo";
        }

        workExperiencesRepostory.save(workExperiences);
        model.addAttribute("rowNumber", workExperiencesRepostory.count());
        return "redirect:/index";
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
    public String addSkilsInfo( Skills skills,Principal principal,Model model) {
        /*Here we allow the user only has to enter 20 skills information and
	    if the user or person tries to enter more than 20 information then submit button will get
	    disable so that they cannot enter more than ten information*/
        Resume resume=resumeRepostory.findByUsername(principal.getName());
        skills.setResume(resume);
        model.addAttribute("disSubmit", skillsRepostory.count() >= 20);
        model.addAttribute("rowNumber", skillsRepostory.count());
        model.addAttribute("newSkill", skills);
        return "addSkillInfo";
    }
    /*This method is used to check the validation for each values has been entered and if it is valid data then it will save it to data base table
     * also store and check  the record of the rows in data base table*/
    @PostMapping("/addSkillInfo")
    public String addSkilsInfo(@Valid @ModelAttribute("newSkill") Skills skills,BindingResult bindingResult,Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("rowNumber", skillsRepostory.count());
            return "addSkillInfo";
        }

        skillsRepostory.save(skills);
        model.addAttribute("rowNumber", skillsRepostory.count());
        return "redirect:/index";
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
    @GetMapping("/addJobInfo")
    public String showJobForm(Model model)
    {
        model.addAttribute("job",new Job());
        return "addJobInfo";
    }
    @PostMapping("/addJobInfo")
    public  String processJob(@Valid @ModelAttribute("job") Job job,
                              BindingResult bindingResult, Model model) {
        jobRepository.save(job);

        return "redirect:/addskilltojob/" + job.getId();
    }

    @GetMapping("/addskilltojob/{id}")
    public String addSkillToJob(@PathVariable("id") long jobID, Model model)
    {

        model.addAttribute("job", jobRepository.findOne(new Long(jobID)));
        model.addAttribute("skilllist",skillsRepostory.findAll());

        return "addSkillToJobInfo";
    }
    @PostMapping("/addskilltojob/{jobid}")
    public String Skilltojob(@PathVariable ("jobid") long id,
                             @RequestParam("job") String jobID,
                             @ModelAttribute("aSkill")Resume p,
                             Model model)
    {
        Job njob=jobRepository.findOne(new Long(id));
        njob.addSkill(skillsRepostory.findOne(new Long(jobID)));
        jobRepository.save(njob);
        return "redirect:/addskilltojob/" + id;
    }
    /*This method is used to modify the existing in forms then update data bas tables according to there modify fields */
    @RequestMapping("/updateJobInfo/{id}")
    public String updateJobInfo(@PathVariable("id") long id, Model model){
        model.addAttribute("newJob", jobRepository.findOne(id));
        return "addJobInfo";
    }
    /*This method is used to delete the existing data  records from data base table and dispaly the rest of data which has been there*/
    @RequestMapping("/deleteJobInfo/{id}")
    public String delJobInfo(@PathVariable("id") long id){
        jobRepository.delete(id);
        return "redirect:/listJobInfo";
    }
    /*This method is used to display the existing data  records from data base table*/
    @RequestMapping("/listJobInfo")
    public String listJobInfo(Model model){
        model.addAttribute("searchJob", jobRepository.findAll());
        return "listJobInfo";
    }


   // @GetMapping("/addSkillToJobInfo")
//    public String addSkilltoJob(Model model) {
//        model.addAttribute("newJob", new Resume());
//        model.addAttribute("skillLists", skillsRepostory.findAll());
//        return "addSkillToJobInfo";
//    }
//
//   @PostMapping("/addSkillToJobInfo")
//    public String addJobInfo(@ModelAttribute("aSkill") Skills skill,
//                             @RequestParam("job")String job_Id ,
//                             BindingResult bindingResult, Principal principal ,Model model) {
//
//        if (bindingResult.hasErrors()) {
//
//            return "addSkillToJobInfo";
//        }
//       Job jobs=new Job();
//       jobs.addSkill(skillsRepostory.findOne(new Long(job_Id)));
//       jobs.setResume(resumeRepostory.findByUsername(principal.getName()));
//       jobRepository.save(jobs);
//       model.addAttribute("person", resumeRepostory.findByUsername(principal.getName()));
//       return "redirect:/listJobInfo";
//   }

    /*******************************Result Info***************************************************************/

    @RequestMapping("/EditResumedetail")
    public String viewResume(Principal principal, Model model) {
        Resume resumeR=resumeRepostory.findByUsername(principal.getName());
        model.addAttribute("resumeR", resumeR);
        model.addAttribute("listEdu",eduAchievementsRepostory.findByResume(resumeR));
        model.addAttribute("listSkill",skillsRepostory.findByResume(resumeR));
        model.addAttribute("listExps",workExperiencesRepostory.findByResume(resumeR));
        return "ResultResumeInfo";
    }

    /*******************************************************************************************/
    @GetMapping("/SummerizedResume/{id}")
    public String summary(Principal principal,Model model) {
        Resume resumeR=resumeRepostory.findByUsername(principal.getName());
        model.addAttribute("resumeR", resumeR);
        model.addAttribute("listEdu",eduAchievementsRepostory.findByResume(resumeR));
        model.addAttribute("listSkill",skillsRepostory.findByResume(resumeR));
        model.addAttribute("listExps",workExperiencesRepostory.findByResume(resumeR));
        return "SummerizedResume";
    }


    @GetMapping("/searchpeople")
    public String searchPeople(Model model) {

        model.addAttribute("user",new Resume());

        return "searchpeople";
    }

    @PostMapping("/searchpeople")
    public String showPeople(@ModelAttribute("user") Resume resumes,
                             Model model) {

        Iterable<Resume>resumeIterable=resumeRepostory.findByFirstname(resumes.getFirstname());
        model.addAttribute("person",resumeIterable);

        return "dispPeople";
    }
    @GetMapping("/searchschool")
    public String searchSchool(Model model) {

        model.addAttribute("newEduu",new EduAchievements());

        return "searchschool";
    }

    @PostMapping("/searchschool")
    public String searchSchool(@ModelAttribute("newEduu") EduAchievements eduAchievements,
                             Model model) {

        Iterable<EduAchievements>listedu=eduAchievementsRepostory.findByUniName(eduAchievements.getUniName());
        model.addAttribute("eduList",listedu);

        return "dispSchool";
    }

    @GetMapping("/listjobs")
    public String jobsListed(Model model)
    {
        model.addAttribute("joblist",jobRepository.findAll());

        return"joblist";
    }
    @GetMapping("/jobdetail/{id}")
    public String jobdetail(Model model)
    {
        model.addAttribute("joblist",jobRepository.findAll());

        return"jobdetail";
    }

//    @GetMapping("/viewresume")
//    public String PostResume( Principal principal,Model model)
//    {
//        model.addAttribute("person",personRepository.findAllByUsername(principal.getName()));
//        return "viewresume";
//    }
//    @GetMapping("/editinfo")
//
//    public String Editperson(Principal principal,Model model)
//    {
//        model.addAttribute("person",personRepository.findAllByUsername(principal.getName()));
//        return "editinfo";
//    }

}