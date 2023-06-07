package com.mpesocial.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import com.mpesocial.api.model.Authority;
import com.mpesocial.api.model.MpeUser;
import com.mpesocial.api.repository.AuthorityRepository;
import com.mpesocial.api.repository.MpeUserRepository;

import javax.management.relation.RoleNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class MpeUserServiceImpl implements MpeUserService {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private final BCryptPasswordEncoder bcryptEncoder;
    private final MpeUserRepository mpeUserRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public MpeUserServiceImpl(BCryptPasswordEncoder bcryptEncoder, MpeUserRepository mpeUserRepository, AuthorityRepository authorityRepository) {
        this.bcryptEncoder = bcryptEncoder;
        this.mpeUserRepository = mpeUserRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MpeUser> mpeUser = mpeUserRepository.findByUsername(username);
        if (mpeUser.isPresent()) {
            return mpeUser.get();
        } else {
            throw new UsernameNotFoundException("Não foi possivel encontrar o usuário " + username);
        }
    }

    @Override
    public Optional<MpeUser> findByUsername(String username) {
        return mpeUserRepository.findByUsername(username);
    }

    @Override
    public MpeUser saveNewMpeUser(MpeUser mpeUser) throws RoleNotFoundException {
        System.err.println("saveNewMpeUser: " + mpeUser);
        mpeUser.setPassword(this.bcryptEncoder.encode(mpeUser.getPassword()));
        mpeUser.setEnabled(true);
        
        Optional<Authority> optionalAuthority = this.authorityRepository.findByAuthority(DEFAULT_ROLE);
        System.err.println("optionalAuthority: " + optionalAuthority);
        
        if (optionalAuthority.isPresent()) {
            Authority authority = optionalAuthority.get();
            Collection<Authority> authorities = Collections.singletonList(authority);
            mpeUser.setAuthorities(authorities);
            System.err.println("Roles: " + mpeUser);
            
            return this.mpeUserRepository.saveAndFlush(mpeUser);
        } else {
            throw new RoleNotFoundException("A role não foi encontrada no usuário " + mpeUser.getUsername());
        }
    }
    
}
