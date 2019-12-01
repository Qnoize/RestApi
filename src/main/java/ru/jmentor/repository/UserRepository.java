package ru.jmentor.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jmentor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User as user JOIN FETCH user.roles WHERE user.username=:username")
    User findByUsername(@Param("username") String username);

    @Query("SELECT user FROM User as user JOIN FETCH user.roles WHERE user.id=:id")
    Optional<User> findById(@Param("id") Long id);

    @Query("FROM User as user LEFT JOIN FETCH user.roles ORDER BY user.id")
    List<User> findAll();
}
