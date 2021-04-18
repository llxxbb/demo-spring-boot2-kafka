package llxxbb.demo.test;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static llxxbb.demo.kafka.TopicConfig.TOPIC;

@Component
public class Consumer {

    @KafkaListener(id = "demo-consumer", topics = TOPIC)
    public void received(String input) throws Exception {
        System.out.println("RECEIVED msg: " + input);
        // 注意：逻辑异常必须捕获，否则消息会堵塞！！，环境异常可以放过，这样kafka 可以自动重试。
        // 可以通过移除下面的注释进行验证。
//        Thread.sleep(1000);
//        throw new Exception("will retry");
    }

    @KafkaListener(id = "demo-user", topics = "user",
            properties = "value.deserializer=llxxbb.demo.test.UserDeserializer")
    public void receivedUser(User<UserOtherInfo> input) throws Exception {
//        System.out.println("RECEIVED user.name: " + input.name);
        System.out.println("RECEIVED user.address: " + input.other.address);
        // 注意：逻辑异常必须捕获，否则消息会堵塞！！，环境异常可以放过，这样kafka 可以自动重试。
        // 可以通过移除下面的注释进行验证。
//        Thread.sleep(1000);
//        throw new Exception("will retry");
    }
}
