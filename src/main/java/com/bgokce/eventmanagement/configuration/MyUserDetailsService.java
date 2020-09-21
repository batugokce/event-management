package com.bgokce.eventmanagement.configuration;

import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.manageperson.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person p = personRepository.findByUserNameWithAuthorities(username);
        return p;
    }
}
