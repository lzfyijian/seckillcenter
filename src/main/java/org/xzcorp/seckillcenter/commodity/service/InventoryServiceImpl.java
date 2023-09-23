package org.xzcorp.seckillcenter.commodity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xzcorp.seckillcenter.commodity.model.Inventory;
import org.xzcorp.seckillcenter.commodity.model.InventoryRepository;
import org.xzcorp.seckillcenter.common.exception.ErrorCode;
import org.xzcorp.seckillcenter.common.exception.SecException;
import org.xzcorp.seckillcenter.common.model.result.Result;
import org.xzcorp.seckillcenter.common.template.FlowTemplate;
import org.xzcorp.seckillcenter.infra.redis.RedisService;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService{

    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private FlowTemplate flowTemplate;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Result lockInventoryForCache(String activityId) {
        return flowTemplate.execute(()->{
            long curValue = redisService.decr("activity:" + activityId, 1);
            log.info("当前内存值value="+curValue);
            if(curValue<=0){
                throw new SecException(ErrorCode.STOCK_EXCEED,"库存已卖光");
            }
            return new Result(true);
        });
    }

    @Override
    @Transactional
    public Result lockInventoryForDB(String activityId) {
        return flowTemplate.execute(()->{
            Optional<Inventory> inventoryOptional = inventoryRepository.findByActivityId(activityId);
            if(inventoryOptional.isEmpty()){
                throw new SecException(ErrorCode.SYSTEM_ERROR,"根据活动id查询库存记录为空");
            }
            Inventory inventory = inventoryOptional.get();
            if(inventory.getTotalCount()<=inventory.getLockedCount()){
                throw new SecException(ErrorCode.STOCK_EXCEED,"库存不足");
            }
            inventory.setLockedCount(inventory.getLockedCount()+1);
            inventoryRepository.save(inventory);
            return new Result(true);
        });
    }
}
