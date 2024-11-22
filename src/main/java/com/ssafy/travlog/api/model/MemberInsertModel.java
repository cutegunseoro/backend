package com.ssafy.travlog.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInsertModel {
    private String loginId;
    private String hashedPassword;
    private String publicId;
    private String displayName;
    private String bio;
}
