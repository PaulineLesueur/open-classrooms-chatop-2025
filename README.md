# ChaTop
![Angular](https://img.shields.io/badge/Angular_14-red?logo=angular&logoColor=white)
![RxJS](https://img.shields.io/badge/RxJS_7-B7178C?logo=reactivex&logoColor=white)
![Java](https://img.shields.io/badge/Java_17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_4-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?logo=springsecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)

**ChaTop** is a REST API developed with **Spring Boot** as part of an OpenClassrooms training project.  
It provides the backend services for a real estate rental platform, handling authentication, users, rentals, messages, and file uploads.

The frontend (Angular) was provided as part of the project.  

The API includes:
- **JWT-based authentication** (register / login)
- **User management**
- **Rental listings** with image upload
- **Messaging system** between users
- **Secure REST endpoints** protected by Spring Security
- **Swagger / OpenAPI documentation** for API exploration

<br>

## Technical Highlights

| Area | Description |
|------|-------------|
| **Framework** | Spring Boot |
| **Language** | Java 17 |
| **Security** | Spring Security + JWT (stateless authentication) |
| **Authentication** | Register / Login with JWT Bearer tokens |
| **Database** | MySQL |
| **ORM** | Spring Data JPA (Hibernate) |
| **API Documentation** | Swagger UI via SpringDoc OpenAPI |
| **Architecture** | Controller / Service / Repository layered architecture |
| **File Upload** | Multipart upload for rental pictures |
| **CORS** | Configured for Angular frontend (`localhost:4200`) |

<br>

## Installation

### 1. Clone the repository
```bash
git clone https://github.com/your-username/chatop-backend.git
cd chatop-backend
```

### 2. Database setup (MySQL)
Open MySQL Workbench and create the database using the script given in `frontend/resources/sql/script.sql`

Ensure you have MySQL user with access to this database. Configure your credentials in `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chatop
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 3. Run the backend
```bash
./mvnw spring-boot:run
```
The API will be available at : `http://localhost:3001`

### 4. Launch the frontend
Install dependencies
```bash
npm install
```

Run the development server
```bash
ng serve
```
Visit the following URL in your browser: `http://localhost:4200/`

You can create new account or login with the demo user <br>
Username : `test@test.com`<br>
Password : `test123`

<br>

## Swagger / API Documentation
Swagger UI is enabled and publicly accessible following this url : `http://localhost:3001/swagger-ui/index.html`

### Testing secured endpoints with Swagger
1. Call `/api/auth/login` with valid credentials
2. Copy the returned JWT token
3. Click **Authorize** in Swagger
4. Enter `Bearer YOUR_JWT_TOKEN`
5. You can now test all secured endpoints directly from Swagger

<br>

## Project structure (backend)
- `controller` : REST controllers exposing API endpoints (Auth, User, Rental, Message)
- `service` : Business logic (authentication, rentals, messages, file upload, users)
- `repository` : Spring Data JPA repositories for database access
- `security` : JWT generation & validation, Custom authentication filter, Spring Security configuration, UserDetails & UserDetailsService implementation
- `DTO` : Request / Response objects used to expose clean API contracts

<br>

## Notes
This project is a **fictional educational project** developed as part of an **OpenClassrooms certification program**. It was created for learning purposes only and does not represent a real-world application or organization.
