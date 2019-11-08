package com.xdsty.chatserver.mq;

import com.xdsty.chatserver.util.Constant;
import com.xdsty.chatserver.util.JsonUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/7 10:37
 */
@Component
public class MsgProducer {

    private static final Logger log = LoggerFactory.getLogger(MsgProducer.class);

    private static Properties properties = new Properties();

    private static Producer<String, String> producer;

    static {
        InputStream in = MsgProducer.class.getClassLoader().getResourceAsStream("kafka-producer.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            log.info("加载生产者配置失败", e);
        }
        producer = new KafkaProducer<>(properties);
    }

    public static <T>void sendMsg(String topic ,T msg)  {
        if(msg == null){
            return;
        }
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,"1", JsonUtil.convertToJson(msg));
        producer.send(record);
    }

    @PreDestroy
    public void preDestroy(){
        producer.close();
    }


}
