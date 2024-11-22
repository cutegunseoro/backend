package com.ssafy.travlog.api;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Assertions;
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

    @Test
    public void testTypeAliasResolution() {
        String alias = "MemberModel"; // Alias should match the class name
        try {
            Class<?> clazz = sqlSessionFactory.getConfiguration().getTypeAliasRegistry().resolveAlias(alias);
            System.out.println("Alias resolved to: " + clazz.getName());
        } catch (Exception e) {
            Assertions.fail("Failed to resolve alias: " + alias, e);
        }
    }
}

