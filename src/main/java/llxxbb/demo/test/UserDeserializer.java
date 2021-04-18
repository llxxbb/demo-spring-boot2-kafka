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
