package com.winner.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
		RedisAutoConfiguration.class,
		RedisRepositoriesAutoConfiguration.class
})
class OrderServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
