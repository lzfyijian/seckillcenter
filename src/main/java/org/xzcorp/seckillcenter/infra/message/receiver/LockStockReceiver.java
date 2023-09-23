package org.xzcorp.seckillcenter.infra.message.receiver;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xzcorp.seckillcenter.biz.BizService;
import org.xzcorp.seckillcenter.common.exception.ErrorCode;
import org.xzcorp.seckillcenter.common.exception.SecException;
import org.xzcorp.seckillcenter.common.model.result.Result;
import org.xzcorp.seckillcenter.common.template.FlowTemplate;
import org.xzcorp.seckillcenter.config.MessageQueueConfig;
import org.xzcorp.seckillcenter.infra.message.model.SecMessage;

import java.util.Map;

@Component
@RabbitListener(queues = MessageQueueConfig.queueName)
public class LockStockReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(LockStockReceiver.class);

    @Autowired
    private BizService bizService;
        @RabbitHandler
        public void process(SecMessage secMessage) {
            Map<String,String> payLoad = (Map<String,String>)secMessage.getData();
            String activityId = payLoad.get("activityId");
            String userId = payLoad.get("userId");
            Result confirmOrderResult = bizService.confirmOrder(secMessage.getId().toString(),activityId,userId);
            if(!confirmOrderResult.isSuccess()){
                throw new SecException(ErrorCode.SYSTEM_ERROR,"消息处理失败");
            }
        }
}
