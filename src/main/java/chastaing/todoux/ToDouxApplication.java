package chastaing.todoux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EntityScan("chastaing.model")
public class ToDouxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDouxApplication.class, args);
	}

}
