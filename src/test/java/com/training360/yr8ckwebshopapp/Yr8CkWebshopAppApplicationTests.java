package com.training360.yr8ckwebshopapp;

        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Yr8CkWebshopAppApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testEncode() {
        System.out.println("admin " + new BCryptPasswordEncoder().encode("admin"));
        System.out.println("user " + new BCryptPasswordEncoder().encode("user"));
    }
}
