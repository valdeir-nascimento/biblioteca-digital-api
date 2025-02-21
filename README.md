# Biblioteca API

## Pré-requisitos

- **Java 21** instalado na sua máquina.
- **Maven** para build e gerenciamento de dependências.
- Uma IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code etc.).

## Como Executar a Aplicação

1. **Clone o repositório:**

```bash
   git clone https://github.com/valdeir-nascimento/biblioteca-digital-api.git
```

2. **Navegue até o diretório do projeto:**

```bash
   cd nome-do-projeto
```

3. **Compile e execute a aplicação:**
   
```bash
   ./mvnw spring-boot:run
```

## Acessando a Documentação da API (Swagger)
Após iniciar a aplicação, você pode explorar e testar os endpoints da API através do Swagger:

```bash
   http://localhost:8080/swagger-ui/index.html
```

## Acessando o Banco de Dados H2
Para visualizar e interagir com o banco de dados H2, acesse o console:

```bash
   http://localhost:8080/h2-console/login.jsp?jsessionid=6be71617e200be4e53542aed35f57d82
```

