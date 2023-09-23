package org.xzcorp.seckillcenter.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xzcorp.seckillcenter.common.exception.ErrorCode;
import org.xzcorp.seckillcenter.common.exception.SecException;
import org.xzcorp.seckillcenter.common.model.result.Result;
import org.xzcorp.seckillcenter.common.template.FlowTemplate;
import org.xzcorp.seckillcenter.order.model.OrderRepository;
import org.xzcorp.seckillcenter.order.model.TradeOrder;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private FlowTemplate flowTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Result createOrder(TradeOrder tradeOrder) {
        return flowTemplate.execute(()->{
            TradeOrder tradeOrderInDB = orderRepository.findByOutRequestId(tradeOrder.getOrderId());
            if(tradeOrderInDB!=null){
                if(tradeOrderInDB.businessEquals(tradeOrder)){
                    return new Result(true,tradeOrderInDB);
                }else{
                    throw new SecException(ErrorCode.ORDER_NOT_BUSINESS_EQUAL,"订单被篡改，请检查订单信息");
                }
            }
            orderRepository.save(tradeOrder);
            return new Result(true,tradeOrder);
        });
    }
}
