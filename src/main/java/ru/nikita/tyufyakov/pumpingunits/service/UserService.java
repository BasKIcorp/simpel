package ru.nikita.tyufyakov.pumpingunits.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikita.tyufyakov.pumpingunits.model.Users;
import ru.nikita.tyufyakov.pumpingunits.repository.UserRepository;

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
