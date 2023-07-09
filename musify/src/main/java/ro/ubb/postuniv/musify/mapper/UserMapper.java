package ro.ubb.postuniv.musify.mapper;

import ro.ubb.postuniv.musify.dto.UserDto;
import ro.ubb.postuniv.musify.dto.UserLoginViewDto;
import ro.ubb.postuniv.musify.dto.UserViewDto;
import ro.ubb.postuniv.musify.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "fullName", expression = "java(user.getFullName())")
    UserViewDto toViewDto(User user);

    List<UserViewDto> toViewDtos(List<User> users);

    UserDto toDto(User user);

    List<UserDto> toDtos(List<User> users);

    @Mapping(target = "fullName", expression = "java(user.getFullName())")
    UserLoginViewDto toLoginViewDto(User user, String accessToken);

    User toEntity(UserDto userDto);
}
