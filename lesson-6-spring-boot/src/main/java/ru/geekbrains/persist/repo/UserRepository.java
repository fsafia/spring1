package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.persist.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    List<User> findByLogin(String login);

    List<User> findByLoginLike(String loginPattern);

//    @PersistenceContext
//    private EntityManager em;
//
//    @Transactional
//    public void insert(User user) {
//        em.persist(user);
//    }
//
//    @Transactional
//    public void delete(Integer id)  {
//        User user = em.find(User.class, id);
//        if (user != null) {
//            em.remove(user);
//        }
//    }
//
//    @Transactional
//    public void update(User user) {
//        em.merge(user);
//    }
//
//    public User findByLogin(String login) {
//        return em.createQuery("from User where login = :login", User.class)
//                .setParameter("login", login)
//                .getSingleResult();
//    }
//
//    public User findById(Integer id) {
//        return em.find(User.class, id);
//    }
//
//    public List<User> getAllUsers() {
//        return em.createQuery("from User", User.class).getResultList();
//    }

}
