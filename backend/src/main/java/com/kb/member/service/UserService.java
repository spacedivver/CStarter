package com.kb.member.service;

import com.kb.member.dto.UserDto;
import com.kb.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kb.member.dto.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(UserDto userDto) {
        // DTO를 엔티티로 변환
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // 암호화 추가 가능
        userRepository.save(user); // DB에 저장
    }

    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email); // 이메일 중복 여부 확인
    }
}

