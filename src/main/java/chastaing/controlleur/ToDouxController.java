package chastaing.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import chastaing.model.User;
import chastaing.todoux.UserRepository;

@RestController
public class ToDouxController {
	
	@Autowired
	private UserRepository userRepo;

	@GetMapping("/")
	public String sayHello() {
		return "hello";
	}
	
	@PostMapping("/process_register")
	public RedirectView processRegister(User user) {
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
	    user.setRoles("ROLE_USER");
	    user.setActive(true);
	    userRepo.save(user);
	    return new RedirectView("/");
	}
	
}
