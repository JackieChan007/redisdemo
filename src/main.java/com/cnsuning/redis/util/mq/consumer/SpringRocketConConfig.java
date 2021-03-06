package com.cnsuning.redis.util.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpringRocketConConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String consumerGroupName;
    private String nameServerAddr;
    private String topicName;
    private DefaultMQPushConsumer consumer;
    private MessageListenerConcurrently messageListener;

    public SpringRocketConConfig(String consumerGroupName, String nameServerAddr, String topicName, MessageListenerConcurrently messageListener) {
        this.consumerGroupName = consumerGroupName;
        this.nameServerAddr = nameServerAddr;
        this.topicName = topicName;
        this.messageListener = messageListener;
    }

    public void init() throws Exception {
        logger.info("开始启动消息消费者服务...");
        //创建一个消息消费者，并设置一个消息消费者组
        consumer = new DefaultMQPushConsumer(consumerGroupName);
        //指定 NameServer 地址
        consumer.setNamesrvAddr(nameServerAddr);
        //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //订阅指定 Topic 下的所有消息
        consumer.subscribe(topicName, "*");
        //注册消息监听器
        consumer.registerMessageListener(messageListener);
        // 消费者对象在使用之前必须要调用 start 初始化
        consumer.start();
        logger.info("消息消费者服务启动成功.");
    }

    public void destroy() {
        logger.info("开始关闭消息消费者服务...");
        consumer.shutdown();
        logger.info("消息消费者服务已关闭.");
    }

    public DefaultMQPushConsumer getConsumer() {
        return consumer;
    }
}
