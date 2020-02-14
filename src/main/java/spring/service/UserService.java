package spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.entity.User;
import spring.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired()
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
        return userRepository.findAll();
    }

    public void saveNewUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public void setTeamId(String username, Long teamId) {
        User user = userRepository.findByUsername(username);
        user.setTeamId(teamId);
        userRepository.save(user);
    }


}
