package com.saditec.platform.controller;

import com.saditec.platform.service.MemberService;
import com.saditec.platform.type.ApiResponse;
import com.saditec.platform.type.TMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class TMemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ApiResponse<TMemberDto> registerMember(@RequestBody TMemberDto memberDto) {
        return new ApiResponse<TMemberDto>().toSuccess(memberService.registerUser(memberDto));
    }
}
