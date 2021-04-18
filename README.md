# spring-boot2-kafka

A demo program for kafka based on spring boot2

## pom.xml

the most important dependency is:

```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

## application.yml

```yaml
spring:
  kafka:
    bootstrap-servers: [server01]:[port],[server02]:[port],...
    producer:
	  # default use `StringSerializer`, but JSON is common use case
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer

    consumer:
      # important! the kafka will block when Exception occur for deserializing so use `ErrorHandlingDeserializer` instead of `JsonDeserializer` here
      value-deserializer: org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
      properties:
        spring:
          deserializer:
            value:
              delegate:
                # `ErrorHandlingDeserializer` will use this to deserialize data
                class: org.springframework.kafka.support.serializer.JsonDeserializer
          json:
            trusted:
              # important setting for safety check, otherwise you can't go on
              # form: [classOne],[classTwo]...
              # the * for all
              packages: "*"
```

other info:

- value-serializer: default is  `StringSerializer`.

## Define topics (optional)

In `TopicConfig`  class (of cause you can rename to other name)  define the Topic you will be used. Each Topic each bean. If Topic bean does not set, kafka will use default settings.

```java
@Bean
public NewTopic [youTopicMethod]() {	// TODO replace method to your own
    return TopicBuilder.name("your-topic-name")	// TODO replace the "your-topic-name"
            .partitions(10)
            .replicas(3)
            .build();
}
```

## Implement producer

Inject `KafkaTemplate` object, then call `send`.

```java
@RestController
public class Producer {
    private final KafkaTemplate<String, String> template;

    // inject `KafkaTemplate`, you can change `String` to any type
    public Producer(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    // send
    @RequestMapping("/send/{msg}")
    public void send(@PathVariable("msg") String msg) {
        template.send(("your-topic-name", msg);
    }
}
```

## Implement consumer

use `@KafkaListener` on method

```java
@Component
public class Consumer {
    @KafkaListener(id = "demo-consumer", topics = ("your-topic-name")
    public void received(String input) throws Exception {
        try{
            // TODO your business logic
        }catch(EnvException envError){
            // this will cause to retry, so sleep a little while.
            Thread.sleep(1000);
            throw envError;
        }catch(Exception e){
			// can't throw it again, otherwise will block the Topic      
        }
    }
}
```

## JSON deserialization for generic type

### define deserializer

- extend a class from `JsonDeserializer<T>`

- `setTypeResolver` at constructor, please call `supper()` first, otherwise `objectMapper` will be null.
- optional: override `deserialize` to catch deserializing exception.

```java
package llxxbb.demo.test;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class UserDeserializer extends JsonDeserializer<User<UserOtherInfo>> {
    public UserDeserializer() {
        super();
        this.setTypeResolver((topic, data, headers) -> objectMapper.getTypeFactory().constructType(new TypeReference<User<UserOtherInfo>>() {
        }));
    }

    @Override
    public User<UserOtherInfo> deserialize(String topic, Headers headers, byte[] data) {
        try {
            return super.deserialize(topic, headers, data);
        } catch (Exception e) {
            // TODO handle the exception
        }
        return null;
    }
}
```

### add deserializer to the `properties` of the `@KafkaListener`

```java
	@KafkaListener(id = "demo-user", topics = "user",
	            properties = "value.deserializer=llxxbb.demo.test.UserDeserializer")
    public void receivedUser(User<UserOtherInfo> input) throws Exception {
		// TODO 
    }

```

## Test

start the project

string test：http://localhost:8080//send/{msg}

object test：http://localhost:8080//user/{name}

see console output.

## reference

[spring kafka](https://docs.spring.io/spring-kafka/docs/current/reference/html)

[producer config](https://kafka.apache.org/documentation/#producerconfigs)

[consumer config](https://kafka.apache.org/documentation/#consumerconfigs)

[@KafkaListener](https://docs.spring.io/spring-kafka/docs/current/reference/html/#kafka-listener-annotation)



