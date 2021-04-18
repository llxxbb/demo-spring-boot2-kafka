package llxxbb.demo.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void sedeTest() throws JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build();
//        ObjectMapper objectMapper = new ObjectMapper();
        User<UserOtherInfo> user = new User<>();
        user.name = "lxb";
        user.age = 100;
        user.other = new UserOtherInfo();
        user.other.address = "china";
        user.other.phone = "88888888888";
        String json = objectMapper.writeValueAsString(user);
        assertEquals("{\"name\":\"lxb\",\"age\":100,\"other\":{\"phone\":\"88888888888\",\"address\":\"china\"}}",json);
        User<UserOtherInfo> user1 = objectMapper.readValue(json, new TypeReference<User<UserOtherInfo>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        assertEquals("china", user1.other.address);
    }

}