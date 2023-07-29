package ro.ubb.postuniv.musify.mapper;

import ro.ubb.postuniv.musify.dto.UserViewDto;
import ro.ubb.postuniv.musify.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperImplTest {

    @BeforeAll
    public static void setup() {
        System.out.println("Setup was executed.");
    }

    @BeforeEach
    public void init() {
        System.out.println("Init.");
    }

    @Test
    @DisplayName("User entity to user view Dto test")
    void givenNullUser_whenMappingToUserViewDto_thenReturnNull() {
        UserMapper userMapper = new UserMapperImpl();
        UserViewDto userViewDto = userMapper.toViewDto(null);

        assertNull(userViewDto);
    }

    @Test
    @DisplayName("User entity to user view Dto test")
    void givenValidUser_whenMappingToUserViewDto_thenReturnCorrectValidDto() {
        Integer id = 1;
        String firstName = "Stefan";
        String lastName = "Bertici";
        String email = "me@gmail.com";
        String encryptedPassword = "1234qwerty";
        String country = "Romania";
        String role = "admin";
        String status = "active";

        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setEncryptedPassword(encryptedPassword);
        user.setCountry(country);
        user.setRole(role);
        user.setStatus(status);

        UserMapper userMapper = new UserMapperImpl();
        UserViewDto userViewDto = userMapper.toViewDto(user);

        assertEquals(userViewDto.getFullName(), firstName + " " + lastName);
    }
}