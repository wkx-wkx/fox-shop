package com.fh;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class FoxShopAdminApplicationTests {

    @Test
    void contextLoads() {
        String encode = new BCryptPasswordEncoder().encode("123");
        System.out.println(encode);
    }

}
