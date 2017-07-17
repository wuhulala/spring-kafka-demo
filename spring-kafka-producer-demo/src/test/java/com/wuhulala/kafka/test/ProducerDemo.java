package com.wuhulala.kafka.test;

import com.wuhulala.kafka.producer.SpringKafkaProducerExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 功能说明: kafka生产者<br>
 * 注意事项: <br>
 * 系统版本: v1.0<br>
 * 开发人员: xueah20964<br>
 * 开发时间: 2017/7/17<br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:kafka-producer.xml")
public class ProducerDemo {

    @Autowired
    private SpringKafkaProducerExample example;

    @Test
    public void test(){
        example.testSend();
    }

}
