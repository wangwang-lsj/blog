package com.wanwan;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.wanwan.entity.TestModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.wanwan.mapper.TestMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.net.PasswordAuthentication;

/**
 * @author 玩玩
 * @description
 * @since 2025/4/26  23:16
 */
@SpringBootTest
public class AdminTest {
    @Resource
    private TestMapper testMapper;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        System.out.println("url");
    }
    @Test
    void test0() {
        System.out.println(testMapper.selectById(1));

    }
    @Test
    void test1() {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        System.out.println(id);
    }
    @Test
    void test2() {
        System.out.println(testMapper.selectById(1));
        for (int i = 0; i < 100; i++) {
            TestModel test = new TestModel();
            test.setName("罗书江");
            test.setAge("22");
            testMapper.insert(test);
        }
    }
    @Test
    void test3() {
        System.out.println(passwordEncoder.encode("123456"));
    }
    @Test
    void test4() {
        Jedis jedis = new Jedis("localhost", 6379);
        try {
            // 提供密码进行身份验证
            jedis.auth("luoshujiang");
            // 测试连接
            System.out.println("Connection to server successfully");
            System.out.println("Server is running: " + jedis.ping());

            // 执行其他 Redis 操作
            jedis.set("test", "test");
            System.out.println("Value of key: " + jedis.get("test"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            jedis.close();
        }
    }
}
