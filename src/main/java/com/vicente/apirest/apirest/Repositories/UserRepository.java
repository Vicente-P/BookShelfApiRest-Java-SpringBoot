package com.vicente.apirest.apirest.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vicente.apirest.apirest.Entities.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
