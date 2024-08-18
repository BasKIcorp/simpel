package org.simpel.pumpingUnits.service;

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
    public boolean isExist(String username){return  userRepository.existsUserByUsername(username);}
}
