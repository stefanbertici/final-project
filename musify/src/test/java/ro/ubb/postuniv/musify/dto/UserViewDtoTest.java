package ro.ubb.postuniv.musify.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserViewDtoTest {

    @Test
    public void givenValidUserViewDto_whenSerializingAndDeserializing_thenResultIsSame() throws JsonProcessingException {
        UserViewDto userViewDto = new UserViewDto(1, "Stefan", "Bertici", "Stefan Bertici",
                "me@gmail.com", "Romania", "admin", "active");

        ObjectMapper mapper = new ObjectMapper();
        String valueAsString = mapper.writeValueAsString(userViewDto);
        UserViewDto deserializedUserViewDto = mapper.readValue(valueAsString, UserViewDto.class);

        assertEquals(userViewDto, deserializedUserViewDto);
    }
}