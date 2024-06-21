package at.bbrz.demo.crud.repositories;

import at.bbrz.demo.crud.entities.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

    //List<Users> findByName(String name);
    
}
