# Bookstore API

API REST para gerenciamento de livros desenvolvida com Java, Spring Boot, Oracle Database, Docker e Docker Compose.

O projeto foi criado como estudo prático de desenvolvimento backend, integração com banco de dados, testes automatizados e containerização.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- Hibernate
- Oracle Database XE 21
- Maven / Maven Wrapper
- JUnit
- Mockito
- Docker
- Docker Compose

## Funcionalidades

A API permite realizar operações CRUD de livros:

- Listar todos os livros
- Buscar livro por ID
- Cadastrar livro
- Atualizar livro
- Excluir livro

Também foram implementados:

- Validação dos dados de entrada
- Tratamento de erros HTTP 400
- Tratamento de erros HTTP 404
- Exceção personalizada para livro não encontrado
- Testes unitários da camada de serviço
- Testes da camada controller

## Estrutura simplificada

~~~text
Cliente
  |
  v
LivroController
  |
  v
LivroService
  |
  v
LivroRepository
  |
  v
Spring Data JPA / Hibernate
  |
  v
Oracle Database
~~~

## Arquitetura com Docker Compose

~~~text
                  compose.yaml
                       |
           +-----------+-----------+
           |                       |
           v                       v
      bookstore-api             oracle-xe
       Spring Boot              Oracle XE
           |                       |
           +--- bookstore-network--+
                                   |
                                   v
                                XEPDB1
                                   |
                                   v
                               BOOKSTORE
                                   |
                                   v
                                 LIVRO
~~~

Os dados do Oracle são persistidos através do volume:

~~~text
oracle-volume
~~~

## Configuração da senha

A senha do banco de dados não fica armazenada no código-fonte.

Crie um arquivo `.env` na raiz do projeto baseado no arquivo `.env.example`.

Exemplo:

~~~text
BOOKSTORE_DB_PASSWORD=SUA_SENHA_DO_ORACLE
~~~

O arquivo `.env` está configurado no `.gitignore` e não deve ser versionado.

## Executando com Docker Compose

Antes de iniciar, certifique-se de que o Docker esteja disponível.

Para subir o ambiente:

~~~bash
docker compose up
~~~

O Compose inicia primeiro o Oracle e aguarda o banco ficar saudável através do `healthcheck`.

Depois disso, a aplicação Spring Boot é iniciada.

A API ficará disponível em:

~~~text
http://localhost:8080
~~~

## Endpoints

### Listar livros

~~~text
GET /livros
~~~

Exemplo:

~~~bash
curl -i http://localhost:8080/livros
~~~

### Buscar livro por ID

~~~text
GET /livros/{id}
~~~

Exemplo:

~~~bash
curl -i http://localhost:8080/livros/1
~~~

### Cadastrar livro

~~~text
POST /livros
~~~

Exemplo:

~~~bash
curl -i -X POST http://localhost:8080/livros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Livro Exemplo",
    "autor": "Autor Exemplo",
    "isbn": "1234567890",
    "preco": 49.90
  }'
~~~

### Atualizar livro

~~~text
PUT /livros/{id}
~~~

Exemplo:

~~~bash
curl -i -X PUT http://localhost:8080/livros/1 \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Livro Atualizado",
    "autor": "Autor Exemplo",
    "isbn": "1234567890",
    "preco": 59.90
  }'
~~~

### Excluir livro

~~~text
DELETE /livros/{id}
~~~

Exemplo:

~~~bash
curl -i -X DELETE http://localhost:8080/livros/1
~~~

Uma exclusão realizada com sucesso retorna:

~~~text
HTTP 204 No Content
~~~

## Tratamento de erros

Quando um livro não é encontrado, a API retorna HTTP 404.

Exemplo:

~~~json
{
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Livro com ID 9999 não encontrado"
}
~~~

Dados inválidos enviados para a API retornam HTTP 400 com as mensagens de validação correspondentes.

## Testes automatizados

Foram implementados testes para as camadas de serviço e controller.

Para executar todos os testes:

~~~bash
./mvnw test
~~~

Somente os testes do serviço:

~~~bash
./mvnw -Dtest=LivroServiceTest test
~~~

Somente os testes do controller:

~~~bash
./mvnw -Dtest=LivroControllerTest test
~~~

Durante o desenvolvimento foram validados:

~~~text
LivroServiceTest       8 testes
LivroControllerTest    9 testes
Total                  17 testes
~~~

## Docker Compose

Subir o ambiente:

~~~bash
docker compose up
~~~

Parar os containers sem removê-los:

~~~bash
docker compose stop
~~~

Iniciar novamente os mesmos containers:

~~~bash
docker compose start
~~~

Verificar os serviços:

~~~bash
docker compose ps
~~~

Mostrar também containers parados:

~~~bash
docker compose ps -a
~~~

Validar o arquivo Compose:

~~~bash
docker compose config -q
~~~

## Persistência

O Oracle utiliza o volume Docker:

~~~text
oracle-volume
~~~

A persistência foi validada com o fluxo:

~~~text
docker compose stop
        ↓
docker compose start
        ↓
GET /livros
        ↓
dados preservados
~~~

## Segurança

A senha do banco não é versionada.

O projeto utiliza:

~~~text
.env          → configuração local e sensível
.env.example  → exemplo seguro para versionamento
~~~

O `.gitignore` também impede o versionamento de arquivos gerados ou locais, incluindo:

~~~text
.env
target/
.vscode/
~~~

## Status do projeto

~~~text
CRUD REST                     OK
Oracle Database               OK
Spring Data JPA               OK
Validações                    OK
Tratamento HTTP 400           OK
Tratamento HTTP 404           OK
Testes automatizados          OK
Docker                        OK
Docker Compose                OK
Healthcheck Oracle            OK
Persistência de dados         OK
~~~

## Objetivo do projeto

Este projeto faz parte de uma trilha prática de estudos voltada para desenvolvimento backend, Java, bancos de dados, APIs REST, testes automatizados e práticas de DevOps.
