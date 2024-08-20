package org.simpel.pumpingUnits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.simpel.pumpingUnits.repository.UserRepository;
import org.simpel.pumpingUnits.service.UserService;

@SpringBootApplication
public class PumpingUnitsApplication  {

    private UserRepository userRepository;

    public PumpingUnitsApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PumpingUnitsApplication.class, args);

    }


   /* @Override
    public void run(ApplicationArguments args) throws Exception {
        UserService userService = new UserService(userRepository);
        userService.addUser("asdasd.ru","456");
        userService.getAllUsers().forEach(users ->
                {
                    System.out.println( users.getEmail());
                    System.out.println(users.getPassword());}
        );
    }*/

}
