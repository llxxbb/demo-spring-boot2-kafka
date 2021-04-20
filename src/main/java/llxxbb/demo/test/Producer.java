package llxxbb.demo.test;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

import static llxxbb.demo.kafka.TopicConfig.TOPIC;

@RestController // 可以是其它注解，这里只是方便测试
public class Producer {
    private final KafkaTemplate<String, Object> template;

    public Producer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    @RequestMapping("/send/{msg}")  // 测试用
    public void send(@PathVariable("msg") String msg) {
        template.send(TOPIC, msg);
    }

    @RequestMapping("/user/{name}")  // 测试用
    public void sendUser(@PathVariable("name") String name) throws ExecutionException, InterruptedException {
        User<UserOtherInfo> user = new User<>();
        user.name = name;
        user.age = 100;
        user.other = new UserOtherInfo();
        user.other.address = "china";
        user.other.phone = "88888888888";
        // 异步发送
        template.send("user", user);
        // 同步发送
        template.send("user", user).get();
    }
}
