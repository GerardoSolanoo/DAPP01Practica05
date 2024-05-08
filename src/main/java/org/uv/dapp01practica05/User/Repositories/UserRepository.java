package org.uv.dapp01practica05.User.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.dapp01practica05.User.Entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}