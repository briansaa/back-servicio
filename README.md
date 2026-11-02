# SADITEC · API de Reservas

API REST para reservar espacios educativos y recreativos (laboratorios, canchas, auditorios) por bloques horarios. Está hecha con **Spring Boot 3**, autenticación **JWT** y **MySQL**.

Frontend del proyecto: [briansaa/front-usuarios](https://github.com/briansaa/front-usuarios) (Next.js).

## Funcionalidades

- Registro de miembros y login con usuario y contraseña. El login devuelve un JWT firmado con HS384.
- Roles y permisos (`USER_ROLE`, `ADMIN_ROLE`) que viajan dentro del token.
- Catálogo de tipos de espacio, espacios y horarios disponibles.
- Reservas con validación: la hora de fin debe ser posterior a la de inicio, y **no se permiten cruces** en el mismo espacio y día.
- Datos de ejemplo al arrancar en local: roles, 3 tipos de espacio, 5 espacios y 4 bloques horarios.
- Respuestas uniformes con `ApiResponse` y manejo global de errores.

## Stack

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.3 (Web, Data JPA, Security) |
| Seguridad | Spring Security, filtros JWT propios (jjwt), BCrypt |
| Base de datos | MySQL 8 / MariaDB (H2 para tests) |
| Tests | JUnit 5, Mockito, AssertJ |
| DevOps | Docker, Docker Compose, GitHub Actions |

## Arquitectura

```
controller/   → endpoints REST
service/      → reglas de negocio (validación de reservas, registro)
repository/   → Spring Data JPA (incluye la consulta de cruces de horario)
entity/ type/ → entidades JPA y DTOs
security/     → configuración, filtros de autenticación/JWT, usuarios y roles
config/       → DataSeeder con datos de ejemplo
error/        → excepciones y @RestControllerAdvice
```

## Cómo correrlo

### Opción 1: Docker Compose (recomendado)

```bash
docker compose up --build
```

La API queda en `http://localhost:10000/apiv1` con MySQL incluido.

### Opción 2: local con Maven

Requisitos: Java 21 y MySQL corriendo en `localhost:3306`.

```bash
cp .env.example .env     # opcional: ajusta credenciales
./mvnw spring-boot:run
```

La base `tec_saditec` se crea sola y se cargan los datos de ejemplo.

### Tests

```bash
./mvnw test   # usa H2 en memoria, no necesita MySQL
```

## Variables de entorno

| Variable | Descripción | Default (perfil `local`) |
|---|---|---|
| `DB_URL` / `DB_USERNAME` / `DB_PASSWORD` | Conexión a MySQL | `localhost:3306/tec_saditec`, `root`, vacío |
| `JWT_SECRET` | Clave Base64 de 256 bits o más | clave de desarrollo |
| `JWT_EXPIRATION_MINUTES` | Duración del token | `90` |
| `CORS_ALLOWED_ORIGINS` | Origen del frontend | `http://localhost:3000` |
| `SPRING_PROFILES_ACTIVE` | `local` o `prod` | `local` |

En el perfil `prod` no hay valores por defecto: todas las variables son obligatorias.

## Endpoints

Todas las rutas van con el prefijo `/apiv1`.

| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| POST | `/member/register` | — | Registrar un miembro |
| POST | `/login` | — | Login: devuelve `accessToken` y `refreshToken` |
| GET | `/educational/recreational/facilities/type` | JWT | Listar tipos de espacio |
| POST | `/educational/recreational/facilities/type` | JWT | Crear un tipo de espacio |
| GET | `/educational/recreational/facilities/type/{id}` | JWT | Espacios de un tipo, con horarios |
| GET | `/educational/recreational/facilities/{id}` | JWT | Detalle de un espacio y sus reservas |
| POST | `/reservation` | JWT | Crear una reserva |

### Ejemplo

```bash
# 1. Registrar
curl -X POST localhost:10000/apiv1/member/register -H 'Content-Type: application/json' \
  -d '{"email":"demo@tecsup.edu.pe","code":"U2024001","password":"Demo1234","firstName":"Ana","lastName":"Perez"}'

# 2. Login
curl -X POST localhost:10000/apiv1/login -H 'Content-Type: application/json' \
  -d '{"username":"demo@tecsup.edu.pe","password":"Demo1234"}'

# 3. Reservar (usa el accessToken del paso 2)
curl -X POST localhost:10000/apiv1/reservation -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' \
  -d '{"educationalAndRecreationalFacility":"<id>","reservationDate":"2026-11-03T00:00:00Z","startTime":"08:00:00","endTime":"10:00:00"}'
```

Si la reserva se cruza con otra, la API responde `400` con *"The facility is already reserved in that time range"*.

## Historial

Ver [CHANGELOG.md](CHANGELOG.md).

## Autores

- **[Brian Saavedra](https://github.com/briansaa)**: backend (API, seguridad JWT, reservas)
- **Abraham Balboa**: frontend y apoyo en el backend
