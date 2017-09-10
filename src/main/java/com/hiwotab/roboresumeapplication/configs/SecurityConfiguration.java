package com.hiwotab.roboresumeapplication.configs;


import com.hiwotab.roboresumeapplication.repository.ResumeRepostory;
import com.hiwotab.roboresumeapplication.services.SSUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SSUserDetailsService userDetailsSerevice;

    @Autowired
    private ResumeRepostory resumeRepostory;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(resumeRepostory);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/","/signUpForm","/editSignUp","/css/**","/js/**","/img/**","/font-awesome/**","/fonts/**","lib/**", "/homePage").permitAll()
                .antMatchers("/addEduInfo","/listEduInfo","/updateEduInfo/**","/addSkillInfo","/listSkillInfo",
                        "/updateSkillInfo/**","/addWorkExpInfo","/listExpInfo","/updateExpInfo/**","/searchPeople",
                        "/searchSchool","/searchCompany","/searchJobs","/listJobs","/EditResumedetail","/SummerizedResume").access("hasAuthority('JOB SEEKERS')")
                .antMatchers("/addJobInfo","/listJobInfo","/listJobs","/updateJobInfo/**").access("hasAuthority('RECRUITERS')")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/index")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll().permitAll()
                .and()
                .httpBasic();
        http
                .csrf().disable();
        http
                .headers().frameOptions().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceBean());
    }
}


