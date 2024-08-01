package com.mms.user.service.repositories;

import com.mms.user.service.model.Role;
import com.mms.user.service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String username);
   /* @Query("""
            SELECT user
            FROM User user
            WHERE user.fullName = :fullName
            OR user.email = :email
            OR user.roles = :userRole
            OR user.accountLocked = :accountLocked
            OR user.enabled = :enabled
            """)
    Page<User> findAllUsers(Pageable pageable, String fullName, String email, Role userRole, boolean accountLocked, boolean enabled);*/
}
