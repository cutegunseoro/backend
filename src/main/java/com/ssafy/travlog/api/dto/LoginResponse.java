package com.ssafy.travlog.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    //    private String refreshToken;
    private PublicMemberInfo publicMemberInfo;
}
