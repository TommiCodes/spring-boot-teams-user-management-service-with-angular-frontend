package com.usermanagement.repository;

import com.usermanagement.model.Role;
import com.usermanagement.model.User;
import com.usermanagement.model.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(Roles role);
}
