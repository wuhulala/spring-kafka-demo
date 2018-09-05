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
public class ComplexCommit {

    private static final Logger logger = LoggerFactory.getLogger(ComplexCommit.class);

    ///////////////////////////// 方法区 ////////////////////////////////////

    public static void main(String[] args) throws InterruptedException {
        Consumer<String, String> consumer =
                KafkaConsumerUtils.getUnAutoConsumer("un-auto-commit-async", "client1");
        consumer.subscribe(Collections.singletonList(KafkaConstants.DEMO_TOPIC));
        try {

            // 如果一切正常，使用快速不阻塞的commitAsync
            // 如果直接关系消费者，就没有所谓的下一次提交了，使用commitSync()方法会一直重试。直到提交成功或者发生无法恢复的错误
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(10000);
                // 每一条提交一次偏移量
                records.forEach(record -> {
                    System.out.printf("partition = %d, offset = %d, key = %s, value = %s\n",
                            record.partition(), record.offset(), record.key(), record.value());

                    consumer.commitAsync();
                });
            }
        } catch(Exception e){
            logger.error("commit failed", e);
        }finally {
            try {
                consumer.commitSync();
            }finally {
                consumer.close();
            }
        }
    }
}
