package ro.ubb.postuniv.musify.mapper;

import ro.ubb.postuniv.musify.dto.UserDTO;
import ro.ubb.postuniv.musify.dto.UserViewDTO;
import ro.ubb.postuniv.musify.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "fullName", expression = "java(user.getFullName())")
    UserViewDTO toViewDto(User user);

    List<UserViewDTO> toViewDtos(List<User> users);

    UserDTO toDto(User user);

    List<UserDTO> toDtos(List<User> users);

    User toEntity(UserDTO userDTO);
}
