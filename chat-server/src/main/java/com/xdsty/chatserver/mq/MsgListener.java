package com.xdsty.chatserver.mq;

import com.alibaba.fastjson.JSONObject;
import com.xdsty.chatserver.service.chatstrategy.AbstractChatStrategy;
import com.xdsty.chatserver.service.chatstrategy.ChatStrategyFactory;
import com.xdsty.chatserver.util.JsonUtil;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/7 13:51
 */
@Component
public class MsgListener implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(MsgListener.class);

    @Value("${consumer.topic}")
    private String topic;

    @Override
    public void run() {
        try {
            listener();
        } catch (IOException e) {
            log.info("启动消费者失败", e);
        }
    }

    private void listener() throws IOException {
        log.info("启动监听....");
        Properties properties = new Properties();
        InputStream in = MsgListener.class.getClassLoader().getResourceAsStream("kafka-consumer.properties");
        properties.load(in);

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topic));
        ConsumerRecords<String, String> consumerRecords = null;
        while (true){
            //1000毫秒拉取一次
            consumerRecords = consumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord<String, String> record : consumerRecords){
                long offset = record.offset();
                int partition = record.partition();
                String key = record.key();
                String value = record.value();
                log.info("++++接收到消息,offset: {}, partition: {}, key: {}, value: {}", offset, partition, key, value);

                JSONObject param = JsonUtil.convertJson2Obj(value);
                String type = param.getString("type");
                //找出策略
                AbstractChatStrategy chatStrategy = ChatStrategyFactory.getChatStrategyByType(type);
                if(chatStrategy != null){
                    //处理数据
                    chatStrategy.dealMqMsg(param);
                }
            }
        }
    }
}
