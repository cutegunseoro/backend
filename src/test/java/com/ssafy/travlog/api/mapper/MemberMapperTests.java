package com.ssafy.travlog.api.mapper;

import com.ssafy.travlog.api.model.MemberInsertModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberMapperTests {
    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void testInsertMember() {
        // Given
        MemberInsertModel memberInsertModel = MemberInsertModel.builder()
                .loginId("test")
                .hashedPassword("test")
                .publicId("test")
                .displayName("test")
                .bio("test")
                .build();

        // When
        int result = memberMapper.insertMember(memberInsertModel);

        // Then
        Assertions.assertEquals(1, result);
    }
}
