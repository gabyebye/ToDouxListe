package chastaing.todoux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ToDouxController {

	
	@GetMapping("/")
	public String sayHello() {
		return "hello";
	}
}
