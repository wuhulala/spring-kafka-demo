package com.wuhulala.kafka.consumer;

import com.wuhulala.kafka.producer.KafkaProducerExample;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {


    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        // kafka 服务器地址
        props.put("bootstrap.servers", "127.0.0.1:9092");
        // 消费者组
        props.put("group.id", "test");
        // 定时的提交offset的值
        props.put("enable.auto.commit", "true");
        // 设置上面的定时的间隔
        props.put("auto.commit.interval.ms", "1000");
        // 连接保持时间，如果zookeeper在这个时间没有接收到心跳，会认为此会话已经挂掉
        props.put("session.timeout.ms", "30000");
        // key 反序列化策略
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value 反序列化策略
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(KafkaProducerExample.TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("partition = %d, offset = %d, key = %s, value = %s\n", record.partition() ,record.offset(), record.key(), record.value());
        }
    }
}