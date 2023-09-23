package org.xzcorp.seckillcenter.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xzcorp.seckillcenter.SeckillcenterApplication;
import org.xzcorp.seckillcenter.activity.model.Activity;
import org.xzcorp.seckillcenter.activity.model.ActivityRepository;
import org.xzcorp.seckillcenter.commodity.model.Commodity;
import org.xzcorp.seckillcenter.commodity.model.CommodityRepository;
import org.xzcorp.seckillcenter.commodity.service.InventoryService;
import org.xzcorp.seckillcenter.common.exception.ErrorCode;
import org.xzcorp.seckillcenter.common.exception.SecException;
import org.xzcorp.seckillcenter.common.model.result.Result;
import org.xzcorp.seckillcenter.common.template.FlowTemplate;
import org.xzcorp.seckillcenter.infra.message.MessageService;
import org.xzcorp.seckillcenter.order.model.TradeOrder;
import org.xzcorp.seckillcenter.order.service.OrderService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class BizServiceImpl implements BizService{

    private static final Logger log = LoggerFactory.getLogger(SeckillcenterApplication.class);

    @Autowired
    private FlowTemplate flowTemplate;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CommodityRepository commodityRepository;

    @Override
    public Result buy(String activityId) {
        return flowTemplate.execute(() -> {
            if (activityId == null) {
                throw new SecException(ErrorCode.ILLEGAL_ARGUMENT, "activityId不能为空");
            }
            //锁定库存
            Result lockStockResult = inventoryService.lockInventoryForCache(activityId);
            if(!lockStockResult.isSuccess()){
                throw new SecException(ErrorCode.getByCode(lockStockResult.getErrorCode()),"锁redis库存失败");
            }
            //发送库存锁定消息
            Map<String, String> payLoad = new HashMap<>();
            payLoad.put("activityId", activityId);
            payLoad.put("userId", "u1");
            messageService.sendMsg("secKill.lockStock", payLoad);
            return new Result(true);
        });
    }

    @Override
    @Transactional
    public Result confirmOrder(String outRequestId,String activityId, String userId) {
        return flowTemplate.execute(()->{
            Optional<Activity> activityOptional = activityRepository.findById(activityId);
            if(activityOptional.isEmpty()){
                throw new SecException(ErrorCode.SYSTEM_ERROR,"活动不存在");
            }
            Optional<Commodity> commodityOptional = commodityRepository.findByCommodityId(activityOptional.get().getCommodityId());
            if(commodityOptional.isEmpty()){
                throw new SecException(ErrorCode.SYSTEM_ERROR,"商品信息不存在");
            }
            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setOutRequestId(outRequestId);
            tradeOrder.setOrderId(UUID.randomUUID().toString());
            tradeOrder.setUserId(userId);
            tradeOrder.setCommodityId(activityOptional.get().getCommodityId());
            tradeOrder.setQuantity(1L);
            tradeOrder.setOrderAmount(commodityOptional.get().getCommodityPrice());
            tradeOrder.setStatus("WAIT_BUYER_PAY");
            Result createOrderResult = orderService.createOrder(tradeOrder);
            if(!createOrderResult.isSuccess()){
                throw new SecException(ErrorCode.getByCode(createOrderResult.getErrorCode()),"创建订单失败");
            }
            Result lockInventoryResult = inventoryService.lockInventoryForDB(activityId);
            if(!lockInventoryResult.isSuccess()){
                throw new SecException(ErrorCode.getByCode(lockInventoryResult.getErrorCode()),"库存锁定失败");
            }
            return new Result(true);
        });
    }
}
