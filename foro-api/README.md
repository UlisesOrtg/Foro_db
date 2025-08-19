# Foro API (Spring Boot + JWT)

API REST para gestionar un foro simple (usuarios y tópicos) desarrollada con Spring Boot 3, Spring Security (JWT) y JPA. Incluye colección de Insomnia para pruebas rápidas.

## Características
- Registro e inicio de sesión con generación de token JWT.
- CRUD de Tópicos (listar, crear, actualizar, eliminar con rol ADMIN).
- Persistencia con JPA/Hibernate.
- Configuración por defecto para MySQL. Dependencias disponibles para H2/PostgreSQL si deseas alternar.

## Requisitos
- Java 17
- Maven 3.9+
- Base de datos MySQL 8 (o compatible)

## Estructura principal
- `src/main/java/com/foro/controller`: Controladores (`AuthController`, `TopicoController`).
- `src/main/java/com/foro/security`: Seguridad (`JwtUtil`, `JwtFilter`, `SecurityConfig`).
- `src/main/java/com/foro/model`: Entidades (`Usuario`, `Topico`).
- `src/main/resources/application.properties`: Configuración.
- `insomnia-foro-api.json`: Colección para Insomnia.

## Configuración
Edita `src/main/resources/application.properties` con tus credenciales de MySQL:

```
spring.datasource.url=jdbc:mysql://localhost:3306/foro_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

Notas:
- Puedes sobreescribir con variables de entorno al ejecutar (por ejemplo en contenedores o CI/CD).
- El proyecto incluye dependencia de H2 y PostgreSQL (scope runtime), pero la configuración por defecto usa MySQL.

## Datos de ejemplo
`src/main/resources/data.sql` inserta dos usuarios de muestra:
- Usuario: `demo@foro.com` / contraseña: `demo123`
- Admin: `admin@foro.com` / contraseña: `admin123`

Las contraseñas en la base están en BCrypt.

## Cómo ejecutar
1. Compilar:
   ```bash
   mvn clean package
   ```
2. Ejecutar (desde Maven):
   ```bash
   mvn spring-boot:run
   ```
   O ejecuta el JAR:
   ```bash
   java -jar target/foro-api-0.0.1-SNAPSHOT.jar
   ```
3. La API quedará disponible en: `http://localhost:8080`

## Autenticación (JWT)
- Autenticación vía header `Authorization: Bearer <TOKEN>`.
- Endpoints públicos: `/auth/**` (registro y login`)
- Resto: requiere token válido.

### Flujo típico
1. Registrar usuario (opcional si usas los de `data.sql`).
2. Iniciar sesión para obtener el token JWT.
3. Usar el token para consumir endpoints protegidos.

## Endpoints principales

### Auth
- POST `/auth/register`
  - Body JSON:
    ```json
    {
      "nombre": "Juan Perez",
      "email": "juan@correo.com",
      "password": "secreto123"
    }
    ```
  - Respuesta: 201 con el usuario creado (sin exponer password en texto plano).

- POST `/auth/login`
  - Body JSON:
    ```json
    {
      "email": "demo@foro.com",
      "password": "demo123"
    }
    ```
  - Respuesta: 200
    ```json
    { "token": "<JWT>" }
    ```

### Tópicos
- GET `/topicos` (protegido)
  - Soporta paginación Spring (parámetros `page`, `size`, `sort`).

- POST `/topicos` (protegido)
  - Body JSON:
    ```json
    {
      "titulo": "Duda sobre Spring",
      "mensaje": "¿Cómo configuro Security?",
      "curso": "Spring Boot",
      "usuarioId": 1
    }
    ```
  - Respuesta: 201 con el tópico creado.

- PUT `/topicos/{id}` (protegido)
  - Body JSON con campos a actualizar (parciales):
    ```json
    {
      "titulo": "Título actualizado"
    }
    ```

- DELETE `/topicos/{id}` (requiere rol `ADMIN`)

Headers de ejemplo para endpoints protegidos:
```
Authorization: Bearer <JWT>
Content-Type: application/json
```

## Probar con Insomnia
- Importa el archivo `insomnia-foro-api.json` en Insomnia.
- Actualiza variables según tu entorno (host, credenciales).

## Errores comunes
- 401 Unauthorized: Token ausente o inválido. Verifica el header `Authorization`.
- 400 Bad Request al crear tópico: `usuarioId` inexistente.
- Conexión a DB: verifica `spring.datasource.*` y que MySQL esté en ejecución.

## Desarrollo y pruebas
- Ejecuta los tests (si agregas):
  ```bash
  mvn test
  ```
- Ajusta el nivel de logs de SQL con `spring.jpa.show-sql=true` para depurar queries.

## Licencia
Este proyecto se ofrece con fines educativos. Adáptalo a tus necesidades y agrega una licencia si vas a distribuirlo.
