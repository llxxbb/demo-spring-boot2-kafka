package llxxbb.demo.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

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
}
