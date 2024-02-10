package domain.dto.mapper;

import domain.dto.response.UserResponse;
import domain.entity.User;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse userToUserResponse(User user);
}
