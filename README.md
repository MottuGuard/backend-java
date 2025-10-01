# MottuGuard (Spring Boot + Thymeleaf + Flyway + Spring Security)

Aplicação web completa para gestão de **motos**, **tags UWB**, **anchors** e **medições** — com **Thymeleaf** (frontend server-side), **Flyway** (migrações), **Spring Security** (login, autorização por perfis) e **PostgreSQL**.

## Sumário

* [Stack & versões](#stack--versões)
* [Pré-requisitos](#pré-requisitos)
* [Subindo o banco (Docker)](#subindo-o-banco-docker)
* [Configuração da aplicação](#configuração-da-aplicação)
* [Executando](#executando)
* [Acessando a aplicação](#acessando-a-aplicação)

---

## Stack & versões

* **Java 21**
* **Spring Boot 3.5.x**
* **Spring MVC / Thymeleaf**
* **Spring Security 6** (form login, roles)
* **Spring Data JPA / Hibernate**
* **Flyway 11.x**
* **PostgreSQL 17**
* **Maven** (wrapper incluso)

---

## Pré-requisitos

* **Java 21** instalado
* **Maven** (opcional – você pode usar o wrapper `./mvnw`)
* **Docker** (opcional – para subir o Postgres rapidamente)

---

## Subindo o banco (Docker)

```bash
docker run -d --name pg-mottu \
  -e POSTGRES_DB=mottu \
  -e POSTGRES_USER=mottu \
  -e POSTGRES_PASSWORD=mottu \
  -p 5432:5432 \
  postgres:17
```

> Se já tiver um Postgres local, apenas crie o DB/usuário `mottu:mottu` (ou ajuste as env vars/`application.properties`).

---

## Configuração da aplicação

A aplicação lê config do **`application.properties`** (perfil padrão) e **variáveis de ambiente**.
Arquivo típico (já presente no projeto):

```properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mottu
spring.datasource.username=mottu
spring.datasource.password=mottu
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

spring.thymeleaf.cache=false

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

server.error.include-message=always
server.port=8080
```

### Usando variáveis de ambiente (opcional)

Você pode sobrescrever as propriedades acima com env vars:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mottu
export SPRING_DATASOURCE_USERNAME=mottu
export SPRING_DATASOURCE_PASSWORD=mottu
export SERVER_PORT=8080
```

> **Dica**: Se receber erro do Flyway “Unsupported Database: PostgreSQL 17.x”, adicione a dependência do **driver específico do Flyway** no `pom.xml`:

```xml
<dependency>
  <groupId>org.flywaydb</groupId>
  <artifactId>flyway-database-postgresql</artifactId>
</dependency>
```

---

## Executando

### 1) Instalar dependências e compilar

```bash
./mvnw clean package
```

### 2) Rodar a aplicação (dev)

```bash
./mvnw spring-boot:run
```

ou rode o jar:

```bash
java -jar target/mottuguard-0.0.1-SNAPSHOT.jar
```

Ao subir, o **Flyway** aplica as migrações automaticamente e a aplicação ficará em:

```
http://localhost:8080
```

---

## Acessando a aplicação

* **UI (Thymeleaf):**

  * Dashboard: `GET /`
  * Motos: `GET /motos`
  * Criar/Editar/Excluir/Vincular Tag: via ações da tela de Motos

* **Autenticação (form login):**

  * Login: `GET /login` (form HTML)
  * Logout: `POST /logout`

> Páginas protegidas exigem autenticação. Ao acessar sem login, você será redirecionado para `/login`.
