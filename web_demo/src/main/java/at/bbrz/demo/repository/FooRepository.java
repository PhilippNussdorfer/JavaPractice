package at.bbrz.demo.repository;

import at.bbrz.demo.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<Foo, Integer> {
    public Foo findByName(String name);
}
