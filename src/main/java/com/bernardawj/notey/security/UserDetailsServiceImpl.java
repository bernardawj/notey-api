package com.bernardawj.notey.security;

import com.bernardawj.notey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<com.bernardawj.notey.entity.User> optUser = this.userRepository.findUserByEmail(email);
        com.bernardawj.notey.entity.User user = optUser.orElseThrow(() -> new UsernameNotFoundException(
                "UserDetailsService.USER_NOT_EXISTS"));

        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
