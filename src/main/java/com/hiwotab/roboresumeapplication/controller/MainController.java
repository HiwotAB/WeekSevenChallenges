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
import java.util.HashSet;
import java.util.Set;

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
        if(skillsRepostory.count()==0){
            Skills skills=new Skills();
            skills.setSkill("Java");
            skills.setRate("Senior");
            skillsRepostory.save(skills);
            Skills skill2=new Skills();
            skill2.setSkill("C++");
            skill2.setRate("Junior");
            skillsRepostory.save(skill2);
            Skills skill3=new Skills();
            skill3.setSkill("C#");
            skill3.setRate("Mid");
            skillsRepostory.save(skill3);
        }


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
    @RequestMapping("/updateUserInfo/{id}")
    public String updateUserInfo(@PathVariable("id") long id, Model model){
        model.addAttribute("newUser", resumeRepostory.findOne(id));
        model.addAttribute("listRoles",userRoleRepo.findAll());
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
            model.addAttribute("skilllist", skillsRepostory.findAll());
            return "addSkillToJobInfo";

    }

    @PostMapping("/addskilltojob/{jobid}")
    public String Skilltojob(@PathVariable ("jobid") long id,
                             @RequestParam("job") String jobID,
                             @ModelAttribute("aSkill")Skills skills,
                             Model model)
    {
        skills=new Skills();
        Job jobs=jobRepository.findOne(new Long(id));
        jobs.addSkill(skillsRepostory.findOne(new Long(jobID)));
        jobRepository.save(jobs);
        return "redirect:/addskilltojob/" + id;
    }

//    @GetMapping("/addSkillForJob")
//    public String addSkilsInfoJob( Skills skills,Model model) {
//        model.addAttribute("newSkill", skills);
//        return " addSkillForJob";
//    }
//
//    @PostMapping("/addSkillForJob")
//    public String addSkilsInfoJob(@Valid @ModelAttribute("newSkill") Skills skills,BindingResult bindingResult,Model model) {
//
//        if (bindingResult.hasErrors()) {
//            return "addSkillInfo";
//        }
//
//        skillsRepostory.save(skills);
//        return "redirect:/addJobInfo";
//    }
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
        return "EditResumedetail";
    }

    /*******************************************************************************************/
    @GetMapping("/SummerizedResume")
    public String summary(Principal principal,Model model) {
        Resume resumeR=resumeRepostory.findByUsername(principal.getName());
        model.addAttribute("resumeR", resumeR);
        model.addAttribute("listEdu",eduAchievementsRepostory.findByResume(resumeR));
        model.addAttribute("listSkill",skillsRepostory.findByResume(resumeR));
        model.addAttribute("listExps",workExperiencesRepostory.findByResume(resumeR));
        return "SummerizedResume";
    }
    //Search a particular person and display the resume
    @GetMapping("/searchPeoples")
    public String searchPeople(Principal principal,Model model) {
        model.addAttribute("newuser",new Resume());
        return "searchPeoples";
    }
    @PostMapping("/searchPeoples")
    public String searchPeople(@ModelAttribute("newuser") Resume resumes,Model model) {
        Iterable<Resume>resumeIterable=resumeRepostory.findAllByFirstname(resumes.getFirstname());
        model.addAttribute("dipUser",resumeIterable);
        return "dispPeopleInfo";
    }
    @GetMapping("/searchSchool")
    public String searchSchool(Model model) {
        model.addAttribute("newEdu",new EduAchievements());
        return "searchSchool";
    }
    @PostMapping("/searchSchool")
    public String searchSchool(@ModelAttribute("newEdu") EduAchievements eduAchievements,Model model) {
        Iterable<EduAchievements>listedu=eduAchievementsRepostory.findByUniName(eduAchievements.getUniName());
        model.addAttribute("eduList",listedu);
        return "dispSchoolInfo";
    }
    @GetMapping("/searchCompany")
    public String searchCompany(Model model) {
        model.addAttribute("newExps",new WorkExperiences());
        return "searchCompany";
    }
    @PostMapping("/searchCompany")
    public String searchCompany(@ModelAttribute("newExps") WorkExperiences workExperiences,Model model) {
        Iterable<WorkExperiences>listorg=workExperiencesRepostory.findByOrgName(workExperiences.getOrgName());
        model.addAttribute("workList",listorg);
        return "dispCompanyInfo";
    }
//To see for the particular job title deatils
    @GetMapping("/listJobs")
    public String listJobs(Model model)    {
        model.addAttribute("listjob",jobRepository.findAll());
        return"listJobInfo";
    }
    @GetMapping("/jobDetailInfo/{id}")
    public String jobDetail(Model model)    {
        model.addAttribute("listjob",jobRepository.findAll());
        return"jobDetailInfo";
    }
    //To see for the particular job title details of employer information
    @GetMapping("/searchJobsByComp")
    public String searchEmployer(Model model) {
        model.addAttribute("listJob", new Job());
        return "searchJobsByComp";
    }
    @PostMapping("/searchJobsByComp")
    public String searchEmployer(@ModelAttribute("listJob") Job jobs,Model model){
        Iterable<Job>jobIterable=jobRepository.findByEmployer(jobs.getEmployer());
        model.addAttribute("listJob",jobIterable);
        return "dispEmpJobDetail";
    }
    @GetMapping("/searchJobsByTitle")
    public String searchJobs(Model model) {
        model.addAttribute("listJob", new Job());
        return "searchJobsByTitle";
    }
    @PostMapping("/searchJobsByTitle")
    public String searchJobs(@ModelAttribute("listJob") Job jobs,Model model){
        Iterable<Job>jobIterable=jobRepository.findByTitle(jobs.getTitle());
        model.addAttribute("listJob",jobIterable);
        return "dispEmpJobDetail";
    }

    @GetMapping("/skillNotifications")
    public String skillMatching(Principal principal, Model model) {
        Resume resume= resumeRepostory.findByUsername(principal.getName());
        Iterable<Job> jobList = jobRepository.findAll();
        Set<Job> jobSet=new HashSet<>();
        if (resume.getSkillsSet().isEmpty())
        {
            model.addAttribute("message", "Enter your skills!");
            return "message";
        }

        else {

            for (Job jobs : jobList) {
                for (Skills jobSkill : jobs.getJobskill()) {
                    for (Skills resumeSkill : resume.getSkillsSet()) {
                        if (jobSkill.getSkill().equals(resumeSkill.getSkill())) {

                            jobSet.add(jobs);
                        } else {
                            System.out.println("no Job found");
                        }

                    }
                }
            }
        }
        model.addAttribute("message", "Some jobs are match your skill");
        model.addAttribute("joblist", jobSet);
        return "skillNotifications";
    }
}