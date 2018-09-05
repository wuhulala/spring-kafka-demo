package com.wuhulala.kafka.commit;

import com.wuhulala.kafka.constants.KafkaConstants;
import com.wuhulala.kafka.utils.KafkaConsumerUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Collections;

/**
 * 利用数据达到只消费一次
 *
 * @author wuhulala<br>
 * @date 2018/9/5<br>
 * @description o_o<br>
 * @since v1.0<br>
 */
public class CommitAndStordToDB {

    class SaveOffsetsRebalance implements ConsumerRebalanceListener{

        public SaveOffsetsRebalance(Consumer<String, String> consumer) {
            this.consumer = consumer;
        }

        private Consumer<String, String> consumer;

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            // 在再均衡之前提交事务。没想清楚
            // commitDBTransaction();
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            for (TopicPartition partition : partitions){
                // 从数据库获取偏移量，再分配到分区之后，从数据库读取此分区，然后开始消费
                consumer.seek(partition, getOffsetFromDB(partition));
            }

        }


    }


    public void init(){
        Consumer<String, String> consumer = KafkaConsumerUtils.getUnAutoConsumer("saved", "c1");
        consumer.subscribe(Collections.singletonList(KafkaConstants.DEMO_TOPIC), new SaveOffsetsRebalance(consumer));

        // 让消费者加入消费者组内
        consumer.poll(0);

        // 从指定位置开始消费
        for (TopicPartition partition: consumer.assignment()){
            consumer.seek(partition, getOffsetFromDB(partition));
        }

        while(true){
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                process(record);
                storeRecordInDB(record);
                // 保存偏移量
                storeOffsetInDB(record.topic(), record.partition(), record.offset());
            }
            //
            commitDBTransaction();
        }
    }

    private void storeOffsetInDB(String topic, int partition, long offset) {
    }

    private void storeRecordInDB(ConsumerRecord<String, String> record) {

    }

    private void process(ConsumerRecord<String, String> record) {
    }

    private void commitDBTransaction() {

    }

    private long getOffsetFromDB(TopicPartition partition) {
        return 0;
    }

}
