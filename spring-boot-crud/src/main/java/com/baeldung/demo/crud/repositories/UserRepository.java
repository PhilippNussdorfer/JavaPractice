package com.baeldung.demo.crud.repositories;

import com.baeldung.demo.crud.entities.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

    //List<Users> findByName(String name);
    
}
