package com.wuhulala.kafka.consumer;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;


/**
 * 功能说明: com.wuhulala.kafka.consumer<br>
 * 注意事项: <br>
 * 系统版本: v1.0<br>
 * 开发人员: xueah20964<br>
 * 开发时间: 2017/7/17<br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:kafka-consumer.xml"})
public class KafkaConsumerExampleTest {

    @Test
    public void test(){

        int i = 0;
        int y = 1;
        System.out.println("---------------------------------" + i + y);
        while (true){

        }
    }

    @Test
    public void t(){
        Map<Long, Map>map = new HashMap<>();
        Map innerMap = new HashMap();
        innerMap.put(Long.parseLong("1") , 1);
        map.put(1L,innerMap);
        System.out.println(map.get(new Long(1)).get(1L));
    }

}