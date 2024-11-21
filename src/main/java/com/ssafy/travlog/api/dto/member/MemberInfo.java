package com.ssafy.travlog.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo {
    private String publicId;
    private String displayName;
    private String bio;
}
