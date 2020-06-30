package com.logistics.checklist.repositories;

import java.util.List;
import java.util.Optional;

import com.logistics.checklist.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    List<User> findByUserRoleId(Long id);

}
