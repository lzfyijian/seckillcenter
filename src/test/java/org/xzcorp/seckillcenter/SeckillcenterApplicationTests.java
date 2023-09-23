package org.xzcorp.seckillcenter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xzcorp.seckillcenter.infra.redis.RedisService;

@SpringBootTest
class SeckillcenterApplicationTests {

	@Autowired
	private RedisService redisUtil;

	@Test
	void contextLoads() {
	}

	@Test
	void testRedis(){
		redisUtil.set("key1","value1");
		System.out.println("redis-get:key=[key],value="+redisUtil.get("key"));
	}

}
