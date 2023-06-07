package com.mpesocial.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpesocial.api.model.MpeUser;

import java.util.Optional;

@Repository
public interface MpeUserRepository extends JpaRepository<MpeUser, Long> {

    Optional<MpeUser> findByUsername(String username);

}
