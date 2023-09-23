package org.xzcorp.seckillcenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.xzcorp.seckillcenter.activity.model.Activity;
import org.xzcorp.seckillcenter.activity.model.ActivityRepository;
import org.xzcorp.seckillcenter.commodity.model.Inventory;
import org.xzcorp.seckillcenter.commodity.model.InventoryRepository;
import org.xzcorp.seckillcenter.infra.redis.RedisService;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SeckillcenterApplication {


	private static final Logger log = LoggerFactory.getLogger(SeckillcenterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SeckillcenterApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ActivityRepository activityRepository,InventoryRepository inventoryRepository, RedisService redisService){
		return args -> {

			List<Activity> activityList = activityRepository.findAll();
			for(Activity activity:activityList){
				Optional<Inventory> inventoryOptional = inventoryRepository.findByActivityId(activity.getActivityId());
				inventoryOptional.ifPresent(value -> {
					String key = "activity:"+value.getActivityId();
					long restCount = value.getTotalCount()-value.getLockedCount();
					log.info("redis写入该key="+key+",value="+restCount);
					redisService.set("activity:"+value.getActivityId(),restCount);
				});
			}
		};
	}

}
