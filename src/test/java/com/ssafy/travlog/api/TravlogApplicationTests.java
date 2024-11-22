package com.ssafy.travlog.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class TravlogApplicationTests {
    @Autowired
    private ApplicationContext context;

    @Test
    public void testMapperBeanLoaded() {
        boolean isMapperBeanPresent = context.containsBean("memberMapper");
        Assertions.assertTrue(isMapperBeanPresent, "MemberMapper bean is not loaded!");
    }
}
