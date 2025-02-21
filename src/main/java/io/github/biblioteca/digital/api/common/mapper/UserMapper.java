package io.github.biblioteca.digital.api.common.mapper;

import io.github.biblioteca.digital.api.common.dto.UserDTO;
import io.github.biblioteca.digital.api.infrastructure.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", expression = "java(dto.id() != null ? dto.id().longValue() : null)")
    @Mapping(target = "creationDate", ignore = true)
    User toEntity(UserDTO dto);

    @Mapping(target = "id", expression = "java(user.getId() != null ? user.getId().intValue() : null)")
    UserDTO toDTO(User user);
}
