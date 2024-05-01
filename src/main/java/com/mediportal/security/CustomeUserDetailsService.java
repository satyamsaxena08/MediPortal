package com.mediportal.security;

import com.mediportal.entity.Role;
import com.mediportal.entity.User;
import com.mediportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomeUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //1st way
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new UsernameNotFoundException("User Not Found")
//        );
        //2nd way
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw  new UsernameNotFoundException("User not Found ");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),mapRolesAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesAuthorities(Set<Role> roles){
        return  roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }

}
