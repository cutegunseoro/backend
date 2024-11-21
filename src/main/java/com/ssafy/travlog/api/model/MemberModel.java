package com.ssafy.travlog.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberModel {
    private Long memberId;
    private String loginId;
    private String hashedPassword;
    private String publicId;
    private String displayName;
    private String bio;
    private LocalDateTime createdAt;
}
