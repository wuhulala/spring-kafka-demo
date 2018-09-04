package com.wuhulala.kafka.utils;

import com.wuhulala.kafka.constants.KafkaConstants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

/**
 * 0_0 o^o
 *
 * @author wuhulala<br>
 * @date 2018/8/29<br>
 * @description o_o<br>
 * @since v1.0<br>
 */
public class KafkaProducerUtils {


    ///////////////////////////// 方法区 ////////////////////////////////////

    public static<K,V> Producer<K,V> getDefaultProducer(){
        Properties props = new Properties();
        // kafka服务器地址
        props.put("bootstrap.servers", KafkaConstants.KAFKA_BROKER_LIST);
        // 需要收到多少个服务器的确认信号，all会保证集群leader和所有备份都返回确认信号 1保证leader返回确认 0 表示不关心返回
        //
        props.put("acks", "1");
        // 失败重试次数
        props.put("retries", 0);

        // 表示一个批次的字节数，当多个消息需要发送到同一分区的时候，生产者会把他们放在同一个批次内，
        props.put("batch.size", 16384);
        // 发送延迟,如果批次被填满，还没有到这个时间，那么会会发送，如果批次没有被填满，到了这个时间，也会发送。 ms
        props.put("linger.ms", 1);

        // 生产者标识
        //props.put("client.id", "hello-1");

        // 生产者发送数据时缓冲区的内存大小
        props.put("buffer.memory", 33554432);

        // 消息压缩算法 snappy综合性价比好 gzip耗cpu
        //props.put("compression.type", "snappy");

        //props.put("batch.size", "snappy");

        //key的序列化策略
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //value的序列化策略
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //写入分区的策略
        //props.put("partitioner.class", "com.wuhulala.kafka.partitioner.MoldPartitioner");
        return new KafkaProducer<K, V>(props);
    }
}
