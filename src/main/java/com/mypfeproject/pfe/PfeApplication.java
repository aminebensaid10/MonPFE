package com.mypfeproject.pfe;

import com.mypfeproject.pfe.entities.Role;
import com.mypfeproject.pfe.entities.User;
import com.mypfeproject.pfe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PfeApplication implements CommandLineRunner {
@Autowired
private UserRepository userRepository ;
	public static void main(String[] args) {
		SpringApplication.run(PfeApplication.class, args);
	}

	public void run(String... args){
		User gestionnaireRHAccount=userRepository.findByRole(Role.GESTIONNAIRERH);
		/*if(null ==gestionnaireRHAccount){
			User user = new User();
			user.setEmail("testeur@gmail.com");
			user.setNom("gestionnaireRH");
			user.setPrenom("gestionnaireRH");
		    user.setRole(Role.GESTIONNAIRERH);
	        user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}*/
	}
}
