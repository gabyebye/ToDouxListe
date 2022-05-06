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

	//index principale cest la vue du projet ou on peut manipuler les tache 
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
	
	@GetMapping("/error")
	public ModelAndView erroren() {
		ModelAndView model = new ModelAndView("error");
		return model;
	}
	
	//permet d'enregistrer en bd l'utilisateur et redirige sur la page de connexion
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
	
	//nous donne l(id de l'utilisateur connecterr
	public int getIdFromUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<User> u = userRepo.findByUserName(auth.getName());

		return u.get().getId();
	}
	
	//permet de nous donner l(utilisateur connecter
	public User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<User> u = userRepo.findByUserName(auth.getName());

		return u.get();
	}
	
	//render la vue de toutes les taches finis
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
