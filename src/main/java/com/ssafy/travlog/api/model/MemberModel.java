package com.ssafy.travlog.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberModel {
    private Long member_id;
    private String login_id;
    private String hashed_password;
    private String public_id;
    private String display_name;
    private String bio;
    private LocalDateTime created_at;
}
