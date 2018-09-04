package com.wuhulala.kafka;

import com.wuhulala.kafka.constants.KafkaConstants;
import com.wuhulala.kafka.utils.KafkaConsumerUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;


/**
 * 0_0 o^o
 *
 * @author wuhulala<br>
 * @date 2018/8/30<br>
 * @description o_o<br>
 * @since v1.0<br>
 */
public class KafakConsumerTest {


    @Before
    public void init(){
    }

    @After
    public void destroy(){

    }

    ///////////////////////////// 方法区 ////////////////////////////////////

    /**
     * 测试慢消费者
     */
    @Test
    public void testSlowConsumer(){
        Consumer<String, String> consumer;

        consumer = KafkaConsumerUtils.getDefaultConsumer("slow-consumers", "client-" + new Date().getTime());

        consumer.subscribe(Collections.singletonList(KafkaConstants.DEMO_TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(record -> {
                System.out.printf("partition = %d, offset = %d, key = %s, value = %s\n",
                        record.partition(), record.offset(), record.key(), record.value());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        //consumer.close();
    }

}
