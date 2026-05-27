# # Hécate Restaurant Management

Aplicación web full stack desarrollada con Angular y Spring Boot para la gestión de reservas de restaurante.

---

# Tecnologías utilizadas

## Frontend
- Angular 21
- TypeScript
- Angular Signals
- Reactive Forms
- RxJS
- Angular Router

## Backend
- Java 21
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- H2 Database

---

# Arquitectura del proyecto

```text
frontend/        -> Aplicación Angular
src/             -> Backend Spring Boot
BackJava/        -> Backend antiguo/pruebas
```

---

# Funcionalidades principales

- Registro de usuarios
- Inicio de sesión con JWT
- Gestión de reservas
- Perfil de usuario
- API REST protegida
- Relación 1:M Usuario → Reservas

---

# Relación de base de datos

## Usuario
Un usuario puede tener muchas reservas.

## Reserva
Cada reserva pertenece a un usuario.

```text
Usuario 1 ---- * Reserva
```

---

# Requisitos

Antes de ejecutar el proyecto necesitas:

- Node.js 20+
- Angular CLI
- Java 21
- Maven

---

# Instalación del frontend

Ir a la carpeta frontend:

```bash
cd frontend
```

Instalar dependencias:

```bash
npm install
```

Ejecutar Angular:

```bash
ng serve
```

El frontend estará disponible en:

```text
http://localhost:4200
```

---

# Instalación del backend

Ir a la carpeta principal del backend:

```bash
cd backend
```

Ejecutar Spring Boot:

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

El backend estará disponible en:

```text
http://localhost:8080
```

---

# Base de datos H2

La aplicación utiliza una base de datos H2 en memoria.

Consola H2:

```text
http://localhost:8080/h2-console
```

Parámetros:
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: vacío

---

# API REST

## Endpoints principales

### Usuarios
- GET `/users`
- GET `/users/{id}`

### Reservas
- GET `/reservations`
- POST `/reservations`

### Autenticación
- POST `/auth/login`
- POST `/auth/register`

---

# Características Angular implementadas

- Standalone Components
- Signals
- Reactive Forms
- Observables
- Servicios HTTP
- Routing
- Guards
- Interceptors JWT

---

# Seguridad

La autenticación se realiza mediante JWT usando Spring Security.


---

# Autor

Ines LLopis
Jose Gallud
Marcos Ivars
Ignacio Baeza