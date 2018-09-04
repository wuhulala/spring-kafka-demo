package com.wuhulala.kafka;

import org.apache.kafka.clients.producer.Producer;

import com.wuhulala.kafka.constants.KafkaConstants;
import com.wuhulala.kafka.utils.KafkaProducerUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 0_0 o^o
 *
 * @author wuhulala<br>
 * @date 2018/8/29<br>
 * @description o_o<br>
 * @since v1.0<br>
 */
public class KafkaSimpleSendTest {

    private Producer<String, String> producer = KafkaProducerUtils.getDefaultProducer();

    ///////////////////////////// 方法区 ////////////////////////////////////

    @Test
    public void testSync() throws ExecutionException, InterruptedException {
        Future<RecordMetadata> recordMetadataFuture = producer.send(new ProducerRecord<String, String>(KafkaConstants.DEMO_TOPIC, "1", "hello 1"));

        RecordMetadata recordMetadata = recordMetadataFuture.get();
        System.out.println(String.format("发送成功: %s", recordMetadata.toString()));
        recordMetadataFuture = producer.send(new ProducerRecord<String, String>(KafkaConstants.DEMO_TOPIC, "2", recordMetadata.topic()));
        System.out.println(String.format("发送成功: %s", recordMetadataFuture.get()));
        recordMetadataFuture = producer.send(new ProducerRecord<String, String>(KafkaConstants.DEMO_TOPIC, "3", "hello 3"));
        System.out.println(String.format("发送成功: %s", recordMetadataFuture.get()));
        producer.close();
    }

    /**
     * 发送消息不需要等待之前的相应
     */
    @Test
    public void testAsync() throws InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(KafkaConstants.DEMO_TOPIC, "1", "hello 1");
        producer.send(record, (recordMetadata, e) -> {
            System.out.println("1::" + recordMetadata);
            if (e != null) {
                e.printStackTrace();
            }
        });
        record = new ProducerRecord<String, String>(KafkaConstants.DEMO_TOPIC, "2", "hello 2");

        producer.send(record, (recordMetadata, e) -> {
            System.out.println("2::" + recordMetadata);
            if (e != null) {
                e.printStackTrace();
            }
        });
        record = new ProducerRecord<String, String>(KafkaConstants.DEMO_TOPIC, "3", "hello 3");

        producer.send(record, (recordMetadata, e) -> {
            System.out.println("3::" + recordMetadata);
            if (e != null) {
                e.printStackTrace();
            }
        });
        Thread.sleep(20000);
        producer.close();
    }


}
