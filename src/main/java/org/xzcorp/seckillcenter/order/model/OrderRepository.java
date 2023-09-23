package org.xzcorp.seckillcenter.order.model;

import org.springframework.data.repository.CrudRepository;
import org.xzcorp.seckillcenter.activity.model.Activity;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends CrudRepository<TradeOrder,String> {

    Optional<TradeOrder> findById(String orderId);

    TradeOrder findByOutRequestId(String outRequestId);

    TradeOrder save(TradeOrder tradeOrder);

}
