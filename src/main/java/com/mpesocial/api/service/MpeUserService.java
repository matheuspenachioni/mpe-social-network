package com.mpesocial.api.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mpesocial.api.model.MpeUser;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

public interface MpeUserService extends UserDetailsService {

    Optional<MpeUser> findByUsername(String username);

    MpeUser saveNewMpeUser(MpeUser mpeUser) throws RoleNotFoundException;

}
