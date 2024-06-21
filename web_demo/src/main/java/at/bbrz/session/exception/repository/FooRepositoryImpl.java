package at.bbrz.session.exception.repository;

import at.bbrz.demo.model.Foo;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("exception")
@Repository
public class FooRepositoryImpl implements FooRepository {
    @Autowired
    private EntityManagerFactory emf;

    @Override
    public void save(Foo foo) {
        emf.unwrap(SessionFactory.class).getCurrentSession().saveOrUpdate(foo);
    }

    @Override
    public Foo get(Integer id) {
        return emf.unwrap(SessionFactory.class).getCurrentSession().get(Foo.class, id);
    }
}