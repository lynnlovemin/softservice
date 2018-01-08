package com.lynn.user.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UnitTest {

    @Autowired
    private StringRedisTemplate template;

    @Test
    public void testRedis(){
        try {
            template.opsForValue().set("min","lynn");
        }catch (Exception e){
           // e.printStackTrace();
        }
    }
}
