package org.simpel.pumpingUnits.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.simpel.pumpingUnits.model.Users;
import org.simpel.pumpingUnits.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users saveUser(Users user){
        return userRepository.save(user);
    }
    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }
    public boolean isExist(String username){return  userRepository.existsUserByEmail(username);}
    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Users) authentication.getPrincipal();
    }
    public String getEmailForCurrentUser() {
        Users currentUser = getCurrentUser();
        return userRepository.findById(currentUser.getId())
                .map(Users::getEmail)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }
}
