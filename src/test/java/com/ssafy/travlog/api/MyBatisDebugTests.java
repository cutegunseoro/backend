package com.ssafy.travlog.api;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisDebugTests {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void testLoadedMappers() {
        System.out.println("Loaded Mappers:");
        sqlSessionFactory.getConfiguration().getMappedStatementNames()
                .forEach(System.out::println);
    }
}

