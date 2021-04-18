package llxxbb.demo.kafka;

import llxxbb.demo.test.User;
import llxxbb.demo.test.UserOtherInfo;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@Configuration
public class TopicConfig {

    public static final String TOPIC = "lxbTopic";

    @Bean
    public NewTopic lxbTest() {
        return TopicBuilder.name(TOPIC)
                .partitions(10)     // 分区数，可提升并发能力。 10为建议值
                .replicas(3)        // 消息的副本， 3为建议值
                .build();
    }


//
//    @Bean("userContainer")
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,User<UserOtherInfo>>> userContainer() {
//        ConcurrentKafkaListenerContainerFactory<String, User<UserOtherInfo>> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        DefaultKafkaConsumerFactory<String, User<UserOtherInfo>> consumerFactory = new DefaultKafkaConsumerFactory<String, User<UserOtherInfo>>();
//        factory.setConsumerFactory(consumerFactory);
////        JsonMessageConverter messageConverter = new JsonMessageConverter();
////        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
////        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
////        messageConverter.setTypeMapper(typeMapper);
////        factory.setMessageConverter(messageConverter);
//        return factory;
//    }

}
