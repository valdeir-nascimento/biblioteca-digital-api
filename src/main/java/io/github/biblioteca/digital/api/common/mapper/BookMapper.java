package io.github.biblioteca.digital.api.common.mapper;

import io.github.biblioteca.digital.api.common.dto.BookDTO;
import io.github.biblioteca.digital.api.infrastructure.model.Book;
import io.github.biblioteca.digital.api.infrastructure.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "title", source = "title")
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUser")
    Book toEntity(BookDTO dto);

    @Mapping(target = "id", expression = "java(book.getId() != null ? book.getId().intValue() : null)")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "userId", expression = "java(book.getUser() != null ? book.getUser().getId().intValue() : null)")
    BookDTO toDTO(Book book);

    @Mapping(target = "title", source = "title")
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUser")
    void copyToProperties(BookDTO dto, @MappingTarget Book book);

    @Named("mapUser")
    default User mapUser(Integer userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId.longValue());
        return user;
    }
}
