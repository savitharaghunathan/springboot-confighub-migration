# Config Hub — Spring Boot 3.5.x Migration Test Application

A Spring Boot web application that manages application configurations across multiple backends. This project serves as a **migration test target** for the [Spring Boot 4.0 migration](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide).

Every major Spring Boot 3.5.x API pattern documented in the SB 4.0 migration guide is deliberately exercised in this codebase, making it a comprehensive target for automated migration tooling (e.g., Konveyor/Kai).

## Tech Stack

- **Java 17** (source/target compatibility)
- **Spring Boot 3.5.14**
- **Spring Data JPA** with H2 (default profile)
- **Spring Security** with OAuth2 client/resource server
- **Spring Batch**, **Spring Kafka**, **Spring AMQP**
- **Spring Data MongoDB**, **Redis**, **Elasticsearch** (profile-activated)
- **Jackson 2** (com.fasterxml.jackson)
- **Flyway** for database migrations
- **JUnit 5** + **Mockito** for testing
- **Maven** build

## Quick Start

### Prerequisites

- Java 17+ (tested with Java 21)
- Maven 3.6+

### Build and Test

```bash
mvn clean verify
```

### Run the Application

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080` with H2 in-memory database.

## REST Endpoints

### Configs — `GET /api/configs`

List all active configurations.

```bash
curl http://localhost:8080/api/configs
```

### Get Config — `GET /api/configs/{key}`

```bash
curl http://localhost:8080/api/configs/app.name
```

### Create Config — `POST /api/configs`

```bash
curl -X POST http://localhost:8080/api/configs \
  -H "Content-Type: application/json" \
  -d '{"key":"app.name","value":"Config Hub","description":"Application name"}'
```

### Update Config — `PUT /api/configs/{key}`

```bash
curl -X PUT http://localhost:8080/api/configs/app.name \
  -H "Content-Type: application/json" \
  -d '{"value":"New Value"}'
```

### Delete Config — `DELETE /api/configs/{key}`

```bash
curl -X DELETE http://localhost:8080/api/configs/app.name
```

### Audit Info — `GET /api/configs/audit`

```bash
curl http://localhost:8080/api/configs/audit
```

## Project Structure

```
src/main/java/com/example/confighub/
├── ConfigHubApplication.java          Spring Boot entry point (@EntityScan)
├── config/
│   ├── WebConfig.java                 HttpMessageConverters bean
│   ├── SecurityConfig.java            OAuth2 + PathRequest config
│   ├── ServerConfig.java              Undertow conditional config
│   ├── MongoConfig.java               MongoDB profile config
│   ├── RedisConfig.java               Redis profile config
│   ├── KafkaConfig.java               Kafka Streams customizer
│   ├── ElasticsearchConfig.java       RestClientBuilderCustomizer
│   └── BatchConfig.java               Spring Batch job config
├── jackson/
│   ├── InstantSerializer.java         @JsonComponent + JsonObjectSerializer
│   ├── InstantDeserializer.java       @JsonComponent + JsonObjectDeserializer
│   ├── AuditMixin.java                @JsonMixin
│   └── ObjectMapperCustomizer.java    Jackson2ObjectMapperBuilderCustomizer
├── controller/
│   └── ConfigController.java          REST endpoints
├── service/
│   ├── ConfigService.java             Core CRUD
│   ├── ConfigRepository.java          Spring Data JPA repository
│   ├── ConfigValidationService.java   Validation with @Nullable/@NonNull
│   ├── ConfigSearchService.java       Elasticsearch (profile)
│   ├── ConfigCacheService.java        Redis (profile)
│   └── ConfigEventService.java        Kafka (profile)
├── model/
│   ├── ConfigEntry.java               JPA entity
│   ├── AuditInfo.java                 Mixin target
│   ├── ConfigDocument.java            MongoDB document
│   └── ConfigEvent.java               Kafka event DTO
├── bootstrap/
│   ├── ConfigBootstrapRegistryInitializer.java  BootstrapRegistry
│   └── ConfigEnvironmentPostProcessor.java      EnvironmentPostProcessor
├── util/
│   ├── PropertyMapperHelper.java      PropertyMapper.alwaysApplyingWhenNonNull()
│   └── NullableUtils.java            Spring @Nullable/@NonNull
└── rabbit/
    └── RabbitRetryConfig.java         RabbitRetryTemplateCustomizer
```

## Spring Boot 4.0 API Coverage

This application exercises **47 distinct Spring Boot 3.5.x API patterns** that require changes when migrating to 4.0. See [docs/MIGRATION_MAPPING.md](docs/MIGRATION_MAPPING.md) for the complete mapping.

### Summary of covered areas

| Area | SB 3.5.x APIs Used |
|------|-------------------|
| Jackson 2→3 | `@JsonComponent`, `@JsonMixin`, `JsonObjectSerializer`, `JsonObjectDeserializer`, `Jackson2ObjectMapperBuilderCustomizer`, `com.fasterxml.jackson.*` imports |
| Testing | `@MockBean`, `@SpyBean`, `MockitoTestExecutionListener`, `@AutoConfigureMockMvc(webClientEnabled)`, `TestRestTemplate` |
| Package moves | `BootstrapRegistry`, `EnvironmentPostProcessor`, `@EntityScan`, `PropertyMapping`, `TestRestTemplate` |
| Starter renames | `spring-boot-starter-web`, `spring-boot-starter-oauth2-client/resource-server`, `spring-boot-starter-aop` |
| Property renames | `spring.data.mongodb.*`, `spring.session.redis.*`, `spring.kafka.retry.topic.backoff.random`, `spring.dao.exceptiontranslation`, `management.health.mongo.*` |
| API changes | `PropertyMapper.alwaysApplyingWhenNonNull()`, `HttpMessageConverters`, `StreamsBuilderFactoryBeanCustomizer`, `RabbitRetryTemplateCustomizer`, `RestClientBuilderCustomizer` |
| Nullability | `org.springframework.lang.Nullable`/`NonNull` |
| Build changes | `hibernate-jpamodelgen`, `spring-retry`, `flyway-core`, `liquibase-core`, `<loaderImplementation>CLASSIC</loaderImplementation>` |
| Web/Servlet | `server.forward-headers-strategy`, Undertow, `PathRequest.toStaticResources()` |
| Spring Batch | `spring-boot-starter-batch` (JDBC metadata assumption) |
| spring.factories | `EnvironmentPostProcessor` registration |

## Testing

The project includes **27 tests** across unit tests, MockMvc tests, and integration tests.

### Run Tests

```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=ConfigServiceTest

# Specific test method
mvn test -Dtest=ConfigValidationServiceTest#validateConfig_validKeyAndValue
```

## Profiles

Infrastructure backends are isolated via Spring profiles:

| Profile | Technology | Required Service |
|---------|-----------|-----------------|
| (default) | H2 + JPA + Web | None |
| `mongodb` | MongoDB | MongoDB on localhost:27017 |
| `redis` | Redis + Session | Redis on localhost:6379 |
| `kafka` | Kafka + Streams | Kafka on localhost:9092 |
| `elasticsearch` | Elasticsearch | ES on localhost:9200 |
| `batch` | Spring Batch | None (uses H2) |
| `rabbit` | RabbitMQ | RabbitMQ on localhost:5672 |

## Configuration

All configuration is in `src/main/resources/application.properties` with profile-specific overrides.
