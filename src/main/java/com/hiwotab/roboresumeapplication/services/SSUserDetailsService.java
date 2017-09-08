package com.hiwotab.roboresumeapplication.services;


import com.hiwotab.roboresumeapplication.model.Resume;
import com.hiwotab.roboresumeapplication.model.UserRole;
import com.hiwotab.roboresumeapplication.repository.ResumeRepostory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService {

  private ResumeRepostory resumeRepostory;

  public SSUserDetailsService(ResumeRepostory resumeRepo){
      this.resumeRepostory=resumeRepo;
  }
    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
      try {
          Resume resume = resumeRepostory.findByUsername(username);
          if (resume == null) {
              System.out.println("User not Found with the provided username" + resume.toString());
              return null;
          }
          System.out.println("user from username" + resume.toString());
          return new org.springframework.security.core.userdetails.User(resume.getUsername(), resume.getPassword(),
                  getAuthorities(resume));
      }catch(Exception e){
          throw new UsernameNotFoundException("User not found");
      }
  }
      private Set<GrantedAuthority> getAuthorities(Resume resume) {
          Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
          for (UserRole role : resume.getRoles()) {
              GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getUrole());
              authorities.add(grantedAuthority);

          }
          System.out.println("User Authorities are" + authorities.toString());
          return authorities;
      }
}
