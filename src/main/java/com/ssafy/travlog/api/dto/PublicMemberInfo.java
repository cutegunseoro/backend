package com.ssafy.travlog.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicMemberInfo {
    private String publicId;
    private String displayName;
    private String bio;
}
