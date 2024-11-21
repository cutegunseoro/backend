package com.ssafy.travlog.api.dto.auth;

import com.ssafy.travlog.api.dto.member.MemberInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    //    private String refreshToken;
    private MemberInfo memberInfo;
}
