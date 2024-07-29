package com.mms.order.manager.repositories;

import com.mms.order.manager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
