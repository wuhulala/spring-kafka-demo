package com.wuhulala.kafka.consumer;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 功能说明: com.wuhulala.kafka.consumer<br>
 * 注意事项: <br>
 * 系统版本: v1.0<br>
 * 开发人员: wuhulala<br>
 * 开发时间: 2017/7/17<br>
 */
//@Component
public class KafkaSlowConsumerExample {

    private static int count = 0;

    //@KafkaListener(topics = "demo1", containerFactory = "containerFactory")
    public void onMessage(String data) {
        //模拟业务操作
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("slow 消费:[" + data + " ] 当前消费了:[" + ++count + "]条信息");
    }
}
