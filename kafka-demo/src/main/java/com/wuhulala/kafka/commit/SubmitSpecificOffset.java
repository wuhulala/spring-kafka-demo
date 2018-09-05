package com.wuhulala.kafka.commit;

import com.wuhulala.kafka.constants.KafkaConstants;
import com.wuhulala.kafka.utils.KafkaConsumerUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 0_0 o^o
 *
 * @author wuhulala<br>
 * @date 2018/9/5<br>
 * @description o_o<br>
 * @since v1.0<br>
 */
public class SubmitSpecificOffset {


    ///////////////////////////// 方法区 ////////////////////////////////////

    private static final Logger logger = LoggerFactory.getLogger(SubmitSpecificOffset.class);


    public static void main(String[] args) throws InterruptedException {
        Consumer<String, String> consumer =
                KafkaConsumerUtils.getUnAutoConsumer("submit-specific-async", "client1");
        Map<TopicPartition, OffsetAndMetadata> curOffsets = new HashMap<>();

        consumer.subscribe(Collections.singletonList(KafkaConstants.DEMO_TOPIC));
        try {
            int count = 0;
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(10000);
                // 每一条提交一次偏移量
                records.forEach(record -> {
                    System.out.printf("partition = %d, offset = %d, key = %s, value = %s\n",
                            record.partition(), record.offset(), record.key(), record.value());

                    curOffsets.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1, "no metadata"));

                    consumer.commitAsync();

                });
            }
        } catch (Exception e) {
            logger.error("commit failed", e);
        }
    }
}
