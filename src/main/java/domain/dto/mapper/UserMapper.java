package domain.dto.mapper;

import domain.dto.response.UserResponse;
import domain.entity.User;
import domain.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userRole", source = "userRole")
    UserResponse userToUserResponse(User user);

    default String getEnumValue(UserRole userRole) {
        return userRole.toString();
    }
}
