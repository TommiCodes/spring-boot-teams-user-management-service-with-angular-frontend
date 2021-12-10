package com.usermanagement.repository;

import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // check if a user exists by searching for his email
    boolean existsByEmail(String email);
    // check if a user exists by searching for his username
    boolean existsByUsername(String username);

    Page<User> findAllByTeams(Team team, Pageable pageable);

    User findByEmail(String email);

}
