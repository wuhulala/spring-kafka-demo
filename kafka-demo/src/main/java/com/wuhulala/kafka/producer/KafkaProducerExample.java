package com.wuhulala.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


public class KafkaProducerExample {
    public final static String TOPIC = "demo";

    public static void main(String[] args) {
        Properties props = new Properties();
        // kafka服务器地址
        props.put("bootstrap.servers", "127.0.0.1:9092");
        // 需要收到多少个服务器的确认信号，all会保证集群leader和所有备份都返回确认信号
        props.put("acks", "all");
        // 失败重试次数
        props.put("retries", 0);
        // 批处理字节大小
        props.put("batch.size", 16384);
        // 发送延迟 ms
        props.put("linger.ms", 1);
        //缓存数据的内存大小
        props.put("buffer.memory", 33554432);
        //key的序列化策略
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //value的序列化策略
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //写入分区的策略
        //props.put("partitioner.class", "com.wuhulala.kafka.partitioner.MoldPartitioner");
        Producer<String, String> producer = new KafkaProducer<>(props);
        String[] strings = new String[1000000];
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000000; i++) {
            strings[i] = Integer.toString(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("prepare use time ： [" + (end - start) + "]");

        for (int i = 0; i < 1000000; i++) {
            producer.send(new ProducerRecord<>(TOPIC, strings[i], strings[i]));
        }
        end = System.currentTimeMillis();
        System.out.println("send use time ： [" + (end - start) + "]");
        producer.close();
    }


}