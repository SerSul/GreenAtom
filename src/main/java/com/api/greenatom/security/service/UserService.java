package com.api.greenatom.security.service;

import com.api.greenatom.security.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User save(User user);
    User create(User user);
    User getByUsername(String username);
    User getCurrentUser();
    UserDetailsService userDetailsService();
}

