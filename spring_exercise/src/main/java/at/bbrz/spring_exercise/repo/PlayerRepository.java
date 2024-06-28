package at.bbrz.spring_exercise.repo;

import at.bbrz.spring_exercise.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
}