package com.kb.member.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;  // 클라이언트에서 입력받는 이메일
    private String name;   // 클라이언트에서 입력받는 이름
    private String password; // 클라이언트에서 입력받는 비밀번호
}
