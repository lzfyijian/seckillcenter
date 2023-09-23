package org.xzcorp.seckillcenter.infra.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xzcorp.seckillcenter.config.MessageQueueConfig;
import org.xzcorp.seckillcenter.infra.message.model.SecMessage;

import java.util.UUID;

@Service
public class MessageService {

    private static final  Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String topic,Object msgPayLoad){
        SecMessage message=new SecMessage();
        message.setTopic(topic);
        message.setData(msgPayLoad);
        message.setId(UUID.randomUUID());
        rabbitTemplate.convertAndSend(MessageQueueConfig.topicExchangeName,message.getTopic(),message);
        logger.info("消息发送成功，message="+message);
    }
}
