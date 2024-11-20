package com.kb.member.controller;

import com.kb.member.dto.User;
import com.kb.member.dto.UserDto;
import com.kb.member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        userService.createUser(userDto); // DTO를 서비스에 전달
        return ResponseEntity.ok("회원가입 성공!");
    }
    // 이메일 중복 확인 API
    @GetMapping("/check-duplicate")
    public ResponseEntity<Boolean> checkDuplicate(@RequestParam String email) {
        System.out.println("checkDuplicate 메서드 실행됨. 이메일: " + email);
        boolean isAvailable = userService.isEmailAvailable(email); // email 중복 확인 로직
        return ResponseEntity.ok(isAvailable);
    }
}
