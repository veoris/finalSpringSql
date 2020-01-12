package spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import spring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired(required = true)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(user.getRole()));
    }


    public List<User> getAllUsers() {
        //TODO checking for an empty user list
        return userRepository.findAll();
    }

    public void saveNewUser(User user) throws Exception {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }








    public User setTeamId(String username, Long teamId){
        User user = userRepository.findByUsername(username);
        user.setTeamId(teamId);
        User updateUser = userRepository.save(user);
        return updateUser;
    }








}
