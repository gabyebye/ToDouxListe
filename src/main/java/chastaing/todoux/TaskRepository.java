package chastaing.todoux;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chastaing.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
	List<Task> findByCreatorId(int creatorId);
}
