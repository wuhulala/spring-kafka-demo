package com.wuhulala.kafka;

import com.wuhulala.kafka.constants.KafkaConstants;
import com.wuhulala.kafka.utils.KafkaProducerUtils;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * 0_0 o^o
 *
 * @author wuhulala<br>
 * @date 2018/8/30<br>
 * @description o_o<br>
 * @since v1.0<br>
 */
public class KafkaMessageGenerator {


    ///////////////////////////// 方法区 ////////////////////////////////////
    public static void main(String[] args) {
        Producer<String, String> producer = KafkaProducerUtils.getDefaultProducer();

        for (int i = 1; i <= 1; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(KafkaConstants.DEMO_TOPIC, i + "", "hello_" + i);
            producer.send(record);
        }
        producer.close();
    }
}
