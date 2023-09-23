package org.xzcorp.seckillcenter.biz;

import org.xzcorp.seckillcenter.common.model.result.Result;

public interface BizService {

    public Result buy(String activityId);

    public Result confirmOrder(String outRequestId,String activityId,String userId);
}
