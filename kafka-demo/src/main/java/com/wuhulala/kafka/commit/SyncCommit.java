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
public class SyncCommit {

    private static final Logger logger = LoggerFactory.getLogger(SyncCommit.class);

    ///////////////////////////// 方法区 ////////////////////////////////////

    public static void main(String[] args) throws InterruptedException {
        Consumer<String, String> consumer =
                KafkaConsumerUtils.getUnAutoConsumer("un-auto-commit", "client1");
        consumer.subscribe(Collections.singletonList(KafkaConstants.DEMO_TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(10000);
            KafkaConsumerUtils.printRecords(records);
            Thread.sleep(10000);
            try {
                // 同步提交会由poll方法提交偏移量，所以处理完所有的记录后要确保调用了commitSync。否则还是会有丢失消息的风险。
                // 如果发生了再均衡，从最近一批消息到发生再均衡之间的所有消息都将被重复处理。
                consumer.commitSync();
            } catch (Exception e) {
                logger.error("commit failed", e);
            }
        }


    }
}
