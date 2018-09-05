package com.wuhulala.kafka.commit;

import com.wuhulala.kafka.constants.KafkaConstants;
import com.wuhulala.kafka.utils.KafkaConsumerUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;


/**
 * 0_0 o^o
 *
 * @author wuhulala<br>
 * @date 2018/9/5<br>
 * @description o_o<br>
 * @since v1.0<br>
 */
public class ASyncCommit {

    private static final Logger logger = LoggerFactory.getLogger(ASyncCommit.class);

    ///////////////////////////// 方法区 ////////////////////////////////////

    public static void main(String[] args) throws InterruptedException {
        Consumer<String, String> consumer =
                KafkaConsumerUtils.getUnAutoConsumer("un-auto-commit-async", "client1");
        consumer.subscribe(Collections.singletonList(KafkaConstants.DEMO_TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(10000);
            // 每一条提交一次偏移量
            records.forEach(record -> {
                System.out.printf("partition = %d, offset = %d, key = %s, value = %s\n",
                        record.partition(), record.offset(), record.key(), record.value());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    consumer.commitAsync();
                } catch (Exception e) {
                    logger.error("commit failed", e);
                }
            });
        }
    }
}
