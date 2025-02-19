INSERT INTO usuario (nome, email, data_criacao)
VALUES
  ('Alice Developer', 'alice.developer@example.com', CURRENT_TIMESTAMP),
  ('Bob Coder', 'bob.coder@example.com', CURRENT_TIMESTAMP),
  ('Charlie Programmer', 'charlie.programmer@example.com', CURRENT_TIMESTAMP),
  ('Diana Engineer', 'diana.engineer@example.com', CURRENT_TIMESTAMP);

INSERT INTO livro (titulo, autor, disponivel, usuario_id, data_aluguel)
VALUES
  ('Clean Code', 'Robert C. Martin', TRUE, NULL, NULL),
  ('The Pragmatic Programmer', 'Andrew Hunt and David Thomas', FALSE, 1, CURRENT_TIMESTAMP),
  ('Refactoring', 'Martin Fowler', TRUE, NULL, NULL),
  ('Design Patterns: Elements of Reusable Object-Oriented Software', 'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides', FALSE, 2, CURRENT_TIMESTAMP),
  ('Domain-Driven Design', 'Eric Evans', TRUE, NULL, NULL),
  ('Working Effectively with Legacy Code', 'Michael Feathers', FALSE, 3, CURRENT_TIMESTAMP),
  ('Continuous Delivery', 'Jez Humble and David Farley', TRUE, NULL, NULL),
  ('Test-Driven Development: By Example', 'Kent Beck', FALSE, 4, CURRENT_TIMESTAMP),
  ('Patterns of Enterprise Application Architecture', 'Martin Fowler', TRUE, NULL, NULL),
  ('Clean Architecture', 'Robert C. Martin', FALSE, 1, CURRENT_TIMESTAMP),
  ('The Clean Coder', 'Robert C. Martin', TRUE, NULL, NULL),
  ('Peopleware: Productive Projects and Teams', 'Tom DeMarco and Timothy Lister', FALSE, 2, CURRENT_TIMESTAMP);
