CREATE TABLE livro (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    disponivel BOOLEAN NOT NULL DEFAULT TRUE,
    usuario_id BIGINT NULL,
    data_aluguel TIMESTAMP NULL,
    CONSTRAINT fk_livro_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE SET NULL
);