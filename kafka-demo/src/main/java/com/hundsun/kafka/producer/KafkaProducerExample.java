package com.hundsun.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


public class KafkaProducerExample {
    public final static String TOPIC = "demo";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class", "com.hundsun.kafka.partitioner.MoldPartitioner");
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