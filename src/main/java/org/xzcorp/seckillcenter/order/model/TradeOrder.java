package org.xzcorp.seckillcenter.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TradeOrder {

    @Id
    private String orderId;

    private String outRequestId;

    private String userId;

    private String commodityId;

    private Long orderAmount;

    private Long quantity;

    private String status;

    public TradeOrder() {
    }

    public TradeOrder(String orderId, String userId, String commodityId, Long orderAmount, Long quantity) {
        this.orderId = orderId;
        this.userId = userId;
        this.commodityId = commodityId;
        this.orderAmount = orderAmount;
        this.quantity = quantity;
    }

    public boolean businessEquals(TradeOrder tradeOrder){
        return this.userId.equals(tradeOrder.getOrderId())
                && this.commodityId.equals(tradeOrder.getCommodityId())
                && this.orderAmount.equals(tradeOrder.getOrderAmount())
                && this.outRequestId.equals(tradeOrder.getOutRequestId());
    }
}
