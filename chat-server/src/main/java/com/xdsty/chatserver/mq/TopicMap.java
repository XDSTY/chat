package com.xdsty.chatserver.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/7 15:25
 */
@Component
public class TopicMap {

    private static final Logger log = LoggerFactory.getLogger(TopicMap.class);

    private static final String TOPIC_CONFIG = "server-topic.properties";

    private static final String SERVER_KEY = "server";

    private static final String TOPIC_KEY = "topic.server";

    private static Map<String, String> topicMap = new HashMap<>(16);

    @PostConstruct
    public void initMap(){
        Properties properties = new Properties();
        InputStream in = TopicMap.class.getClassLoader().getResourceAsStream(TOPIC_CONFIG);
        try {
            properties.load(in);
        } catch (IOException e) {
            log.error("加载服务器对应的topic失败", e);
        }
        for(int i = 0; i < properties.size() / 2; i++){
            String key = properties.getProperty(SERVER_KEY + i);
            String value = properties.getProperty(TOPIC_KEY + i);
            topicMap.put(key, value);
            log.info("{}, {} 加入到topic映射中", key, value);
        }
    }

    /**
     * 根据服务器获取对应的topic
     */
    public static String getTopicByServer(String host){
        return topicMap.get(host);
    }

}
