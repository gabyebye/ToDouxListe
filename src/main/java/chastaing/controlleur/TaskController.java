package chastaing.controlleur;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import chastaing.model.Task;
import chastaing.model.User;
import chastaing.todoux.TaskRepository;
import chastaing.todoux.UserRepository;

@Controller
public class TaskController {
	
	@Autowired
	TaskRepository taskRepo;
	
	@Autowired
	UserRepository userRepo;
	
	public int getIdFromUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<User> u = userRepo.findByUserName(auth.getName());

		return u.get().getId();
	}
	
	 @GetMapping("/addTask")
	 public ModelAndView addTask() {
	  	 ModelAndView model = new ModelAndView("newTask");
	  	 model.addObject("task", new Task());
	  	 return model;
	 }
	 
	 @PostMapping("/process_addtask")
	 public RedirectView processTask(Task task){
		 
		 task.setCreatorId(getIdFromUser());
		 task.setCreationDate(new Date(System.currentTimeMillis()));
		 task.setLastModifiedDate(task.getCreationDate());
		 task.setDone(false);
		 
		 taskRepo.save(task);
		 
		 return new RedirectView("/");
	 }
	 
	 
}