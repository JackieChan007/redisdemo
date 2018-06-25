package com.cnsuning.redis.util.mq.consumer;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RocketMqConsumerListener implements MessageListenerConcurrently {
    private static Logger logger = LoggerFactory.getLogger(RocketMqConsumerListener.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        Date now = new Date();
        String dayString = new SimpleDateFormat("yyyy_MM_dd hh:mm:ss").format(now);
        System.out.println(dayString + ":" + Thread.currentThread().getName() + "Start Receive New Messages: ");
        //还可以将消息落入到数据库
        if (list != null) {
            for (MessageExt ext : list) {
                try {
                    logger.info("监听到消息 : " + new String(ext.getBody(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        //返回消费状态
        //CONSUME_SUCCESS 消费成功
        //RECONSUME_LATER 消费失败，需要稍后重新消费
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
