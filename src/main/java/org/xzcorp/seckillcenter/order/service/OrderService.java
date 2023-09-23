package org.xzcorp.seckillcenter.order.service;

import org.xzcorp.seckillcenter.common.model.result.Result;
import org.xzcorp.seckillcenter.order.model.TradeOrder;

public interface OrderService {

    public Result createOrder(TradeOrder tradeOrder);
}
