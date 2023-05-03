package ro.ubb.postuniv.musify.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserViewDTOTest {

    @Test
    public void givenValidUserViewDto_whenSerializingAndDeserializing_thenResultIsSame() throws JsonProcessingException {
        UserViewDTO userViewDTO = new UserViewDTO(1, "Stefan", "Bertici", "Stefan Bertici",
                "me@gmail.com", "Romania", "admin", "active");

        ObjectMapper mapper = new ObjectMapper();
        String valueAsString = mapper.writeValueAsString(userViewDTO);
        UserViewDTO deserializedUserViewDto = mapper.readValue(valueAsString, UserViewDTO.class);

        assertEquals(userViewDTO, deserializedUserViewDto);
    }
}