package org.xzcorp.seckillcenter.commodity.service;

import org.xzcorp.seckillcenter.common.model.result.Result;

public interface InventoryService {

    Result lockInventoryForCache(String activityId);

    Result lockInventoryForDB(String activityId);
}
