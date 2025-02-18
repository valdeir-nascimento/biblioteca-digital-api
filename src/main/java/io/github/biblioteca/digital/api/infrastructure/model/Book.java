package io.github.biblioteca.digital.api.infrastructure.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "livro")
public class Book {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 255)
    private String title;

    @Column(name = "autor", nullable = false, length = 255)
    private String author;

    @Column(name = "disponivel", nullable = false)
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_livro_usuario"))
    private User user;

    @Column(name = "data_aluguel")
    private LocalDateTime rentalDate;

}
