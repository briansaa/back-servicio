package com.saditec.platform.security.auth.service;

import com.saditec.platform.security.auth.entity.TUserEntity;
import com.saditec.platform.security.auth.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TUserService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<TUserEntity> optionalTUserEntity = userRepository.findByEmailOrCode(username);
        if (optionalTUserEntity.isEmpty()) throw new UsernameNotFoundException("User not found");
        return optionalTUserEntity.get();
    }


}
