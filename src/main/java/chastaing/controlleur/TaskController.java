package chastaing.controlleur;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	//nous donne l'id de la personne connecter
	public int getIdFromUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<User> u = userRepo.findByUserName(auth.getName());

		return u.get().getId();
	}
	
	//render la view permettant d'ajouter une task
	 @GetMapping("/add-task")
	 public ModelAndView addTask() {
	  	 ModelAndView model = new ModelAndView("newTask");
	  	 model.addObject("task", new Task());
	  	 return model;
	 }
	 
	 /*
	 @GetMapping("modifyTask")
	 public ModelAndView modifyTask() {
		 ModelAndView model = new ModelAndView("index");
		 return model;
	 }
	 */
	 
	 //ajoute la task et nous renvoie vers la vue principalle
	 @PostMapping("/processAddtask")
	 public RedirectView processTask(Task task){
		 
		 task.setCreatorId(getIdFromUser());
		 task.setCreationDate(new Date(System.currentTimeMillis()));
		 task.setLastModifiedDate(task.getCreationDate());
		 task.setDone(false);
		 
		 taskRepo.save(task);
		 
		 return new RedirectView("/");
	 }
	 
	 
	 //rend une tache non fini -> fini
	 @PostMapping("/finishTask")
	 public RedirectView processTask(int taskId) {
		 
		 Optional<Task> tToBeDone = taskRepo.findById(taskId);
		 
		 tToBeDone.get().setDone(true);
		 tToBeDone.get().setFinishedDate(new Date(System.currentTimeMillis()));
		 
		 taskRepo.save(tToBeDone.get());
		 
		 return new RedirectView("/");
	 }
	 
	 //rend une tache fini -> non fini
	 @PostMapping("/finishTrueTask")
	 public RedirectView processTrueTask(int taskId) {
		 
		 Optional<Task> tToBeDone = taskRepo.findById(taskId);
		 
		 tToBeDone.get().setDone(false);
		 tToBeDone.get().setFinishedDate(null);
		 
		 taskRepo.save(tToBeDone.get());
		 
		 return new RedirectView("/tache-fini");
	 }
	 
}
