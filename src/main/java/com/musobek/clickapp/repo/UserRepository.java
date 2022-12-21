package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}