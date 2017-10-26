package com.wuhulala.kafka.consumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明: com.hundsun.yht.basic.config<br>
 * 注意事项: <br>
 * <util:map id="kafka-consumer-config">
 *		<description>kafka消费者配置</description>
 *		<entry key="bootstrap.servers" value="${kafka.consumer.bootstrap.servers}" />
 *		<entry key="group.id" value="${kafka.consumer.group.id}"/>
 *		<entry key="enable.auto.commit" value="${kafka.consumer.enable.auto.commit}"/>
 *		<entry key="auto.commit.interval.ms" value="${kafka.consumer.auto.commit.interval.ms}"/>
 *		<entry key="session.timeout.ms" value="${kafka.consumer.session.timeout.ms}"/>
 *		<entry key="key.deserializer" value="${kafka.consumer.key.deserializer}"/>
 *		<entry key="value.deserializer" value="${kafka.consumer.value.deserializer}"/>
 *	</util:map>
 *
 *	<bean id="consumerFactory" class="org.springframework.kafka.core.DefaultKafkaConsumerFactory">
 *		<constructor-arg name="configs" ref="kafka-consumer-config"/>
 *	</bean>
 *
 *	<bean id="containerFactory" class="org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory">
 *		<property name="concurrency" value="4"/>
 *		<property name="consumerFactory" ref="consumerFactory"/>
 *	</bean>
 * 系统版本: v1.0<br>
 * 开发人员: wuhulala<br>
 * 开发时间: 2017/7/20<br>
 */
@Configuration
@EnableKafka
//@ImportResource("classpath*:/spring/spring-kafka-consumer.xml")
public class KafkaConsumerConfig{

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    /**
     * 消费者前缀
     */
    public static final String KAFKA_PRODUCER_PROPERTY_PREFIX = "kafka.consumer.";

    /**
     * 消费者并发线程数
     */
    public static final String KAFKA_PRODUCER_CONFIG_CONCURRENCY = "concurrency";

    /**
     * 消费者拉取消息超时时间配置项
     */
    public static final String KAFKA_POLL_TIMEOUT = "poll.timeout";

    /**
     * 默认拉取消息超时时间 10s
     */
    public static final int DEFAULT_HEARTBEAT_INTERVAL_MS_CONFIG = 10 * 1000;

    /**
     * 默认心跳发送间隔 10s
     */
    public static final String DEFAULT_POLL_TIME_CONFIG = "10000";

    /**
     * 默认fetch最大的等待时间 3s 这样就会3秒发送一条fetch消息
     */
    public static final int DEFAULT_FETCH_MAX_WAIT_MS_CONFIG = 3 * 1000;

    /**
     * 默认fetch的最小值
     */
    public static final int DEFAULT_FETCH_MIN_BYTES_CONFIG = 1;

    private String getKafkaEnvProperty(String key){
        String value = PropertyHolder.getProperty(KAFKA_PRODUCER_PROPERTY_PREFIX + key);
        if(value == null){
            logger.error(key + " should not be null");
        }
        return value;
    }

    private Object getKafkaEnvProperty(String key, Object defaultValue){
        Object value = PropertyHolder.getProperty(KAFKA_PRODUCER_PROPERTY_PREFIX + key);
        if(value == null){
            return defaultValue;
        }
        return value;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory containerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // 设置消费者线程组大小
        factory.setConcurrency(Integer.parseInt(getKafkaEnvProperty(KAFKA_PRODUCER_CONFIG_CONCURRENCY)));

        String poolTimeOutConfig = (String) getKafkaEnvProperty(KAFKA_POLL_TIMEOUT, DEFAULT_POLL_TIME_CONFIG);
        if(StringUtils.isNotEmpty(poolTimeOutConfig) && StringUtils.isNumeric(poolTimeOutConfig)) {
            // 如果没有拿到数据，会重复的调用sendFetches去取数据，直到超过这个时间，会减少commit的次数
            // 和fetch的等待时间结合起来可以减少向服务器频繁的发送消息
            factory.getContainerProperties().setPollTimeout(Long.parseLong(poolTimeOutConfig));
        }

        return factory;
    }

    @Bean
    public ConsumerFactory consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getKafkaEnvProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, getKafkaEnvProperty(ConsumerConfig.GROUP_ID_CONFIG));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, getKafkaEnvProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG));
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, getKafkaEnvProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG));
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, getKafkaEnvProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, getKafkaEnvProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, getKafkaEnvProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));

        // fetch配置,
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, getKafkaEnvProperty(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, DEFAULT_FETCH_MIN_BYTES_CONFIG));

        // fetch超时时间
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, getKafkaEnvProperty(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, DEFAULT_FETCH_MAX_WAIT_MS_CONFIG));
        // 心跳信息
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, getKafkaEnvProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,DEFAULT_HEARTBEAT_INTERVAL_MS_CONFIG));

        return props;
    }

}
