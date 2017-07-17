package com.wuhulala.kafka.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 功能说明: com.wuhulala.kafka.consumer<br>
 * 注意事项: <br>
 * 系统版本: v1.0<br>
 * 开发人员: xueah20964<br>
 * 开发时间: 2017/7/17<br>
 */
@Component
public class KafkaConsumerExample  {

    @KafkaListener(topics = "demo", containerFactory = "containerFactory")
    public void onMessage(String data) {
        System.out.println(data);
    }
}
