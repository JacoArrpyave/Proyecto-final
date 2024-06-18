
package com.proyecto.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.app.Domain.User;


public interface UserRepository extends JpaRepository<User,Long> {
}