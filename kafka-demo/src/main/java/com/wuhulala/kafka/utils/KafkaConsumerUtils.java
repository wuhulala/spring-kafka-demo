package com.wuhulala.kafka.utils;

import com.wuhulala.kafka.constants.KafkaConstants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * 0_0 o^o
 *
 * @author wuhulala<br>
 * @date 2018/8/30<br>
 * @description o_o<br>
 * @since v1.0<br>
 */
public class KafkaConsumerUtils {


    ///////////////////////////// 方法区 ////////////////////////////////////

    public static<K,V> Consumer<K, V> getDefaultConsumer(String groupId, String clientId){
        Properties props = new Properties();
        // kafka 服务器地址
        props.put("bootstrap.servers", KafkaConstants.KAFKA_BROKER_LIST);
        // 消费者组
        props.put("group.id", groupId);
        props.put("client.id", clientId);
        // 定时的提交offset的值
        props.put("enable.auto.commit", "true");
        // 是否从头开始
        //props.put("auto.offset.reset", "earliest");
        // 设置上面的定时的间隔
        props.put("auto.commit.interval.ms", "1000");
        // 连接保持时间，如果zookeeper在这个时间没有接收到心跳，会认为此会话已经挂掉
        props.put("session.timeout.ms", "30000");
        // key 反序列化策略
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value 反序列化策略
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer<>(props);
    }
}
