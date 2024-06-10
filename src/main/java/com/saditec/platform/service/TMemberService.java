package com.saditec.platform.service;

import com.saditec.platform.entity.TMemberEntity;
import com.saditec.platform.error.BadRequestException;
import com.saditec.platform.repository.IMemberRepository;
import com.saditec.platform.security.auth.entity.TRoleEntity;
import com.saditec.platform.security.auth.entity.TUserEntity;
import com.saditec.platform.security.auth.repository.IRoleRepository;
import com.saditec.platform.type.TMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TMemberService {

    private final IMemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;

    @Transactional
    public TMemberDto registerUser(TMemberDto tMemberDto) {

        List<TRoleEntity> roles = roleRepository.findByDescriptionIsIn(tMemberDto.getRoles());

        if (roles.isEmpty()) throw new BadRequestException("No roles found");

        TMemberEntity tMemberEntity = new TMemberEntity();
        tMemberEntity.setEmail(tMemberDto.getEmail());
        tMemberEntity.setPassword(passwordEncoder.encode(tMemberDto.getPassword()));
        tMemberEntity.setCode(tMemberDto.getCode());
        tMemberEntity.setFirstName(tMemberDto.getFirstName());
        tMemberEntity.setLastName(tMemberDto.getLastName());
        tMemberEntity.setCreatedAt(Instant.now());
        tMemberEntity.setUpdatedAt(Instant.now());
        tMemberEntity.setRoles(new HashSet<>(roles));

        memberRepository.save(tMemberEntity);

        return tMemberEntity.toDto();
    }
}
