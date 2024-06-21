package at.bbrz.session.exception.repository;

import at.bbrz.demo.model.Foo;

public interface FooRepository {

    void save(Foo foo);

    Foo get(Integer id);
}
