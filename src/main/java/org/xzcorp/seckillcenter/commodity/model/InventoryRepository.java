package org.xzcorp.seckillcenter.commodity.model;

import org.springframework.data.repository.CrudRepository;
import org.xzcorp.seckillcenter.activity.model.Activity;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {

     Optional<Inventory> findByActivityId(String activityId);

     List<Inventory> findByCommodityId(String commodityId);

     Inventory save(Inventory activity);

}
