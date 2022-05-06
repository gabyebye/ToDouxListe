package chastaing.controlleur;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import chastaing.model.Task;
import chastaing.model.User;
import chastaing.todoux.TaskRepository;
import chastaing.todoux.UserRepository;

@RestController
public class ToDouxController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TaskRepository taskRepo;

	@GetMapping("/")
	public ModelAndView sayHello() {
		ModelAndView model = new ModelAndView("index");
		
		List<Task> tasks = taskRepo.findByCreatorId(getIdFromUser());
		
		List<Task> undoneTasks = new ArrayList();
		
		for(Task t: tasks) {
			 if(!t.getDone()) {
				 undoneTasks.add(t);
			 }
		 }
		
		model.addObject("tasks",undoneTasks);
		model.addObject("username", getUser().getUserName());
		
		return model;
	}
	/*
	@GetMapping("/error")
	public ModelAndView erroren() {
		ModelAndView model = new ModelAndView("error");
		return model;
	}
	*/
	
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
	
	public int getIdFromUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<User> u = userRepo.findByUserName(auth.getName());

		return u.get().getId();
	}
	
	public User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<User> u = userRepo.findByUserName(auth.getName());

		return u.get();
	}
	

	 @GetMapping("/tache-fini")
	 public ModelAndView viewFinishedTask() {
		 ModelAndView model = new ModelAndView("tacheFini");
		
		 List<Task> tasks = taskRepo.findByCreatorId(getIdFromUser());
		 List<Task> doneTasks = new ArrayList();
		 
		 for(Task t: tasks) {
			 if(t.getDone()) {
				 doneTasks.add(t);
			 }
		 }

		 model.addObject("tasks",doneTasks);
		 
		 return model;
	 }
	
}
