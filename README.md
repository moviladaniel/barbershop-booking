# Barber Booking App

## Overview

The Barber Booking App is a Spring Boot application for managing barbershop appointments. Customers can register, book a barber for a service, receive notifications, and leave reviews. Administrators or staff can manage barbers, services, and oversee bookings. The system uses **MySQL** for persistence, **Spring Data JPA** for database interactions, and **Swagger** for API documentation.

## Business Requirements & MVP Features

### Ten Business Requirements (Summary)
1. **User Registration and Login**  
2. **Barber Management** – Admin/staff can add, update, and delete barbers.
3. **Service Management** – Manage services such as haircut, trim, etc.
4. **Appointment (Booking) Creation** – Customers can book appointments with barbers at specific times.
5. **Booking Management** – View, update, and cancel appointments.
6. **Notification System** – Send reminders and status updates.
7. **Payment Tracking** – Indicate if a booking is paid (optional for MVP).
8. **Reviews & Ratings** – Customers can review barbers and services.
9. **Reporting** – Daily/weekly appointment counts and revenue reports.
10. **Scalability & Security** – Design to support multiple locations and secure user data.

### Five MVP Features
1. **User Management**  
   - Register new users and manage user profiles.
2. **Barber & Service Management**  
   - Admin/staff can create, edit, and delete barbers and services.
   - Services include duration and price details.
3. **Booking Creation & Scheduling**  
   - Core functionality: customers select a barber, service, and appointment time.
4. **Booking Management**  
   - Customers can view, update, and cancel appointments.
   - Staff/barbers can view schedules.
5. **Basic Notification/Reminder**  
   - Send or store appointment reminders (via email or in-app).

## System Architecture

### Overview
The system follows a layered architecture:

[ Client (Postman/Browser) ] → [ REST Controller Layer ] → [ Service Layer ] → [ Repository Layer ] → [ MySQL DB ]

- **Spring Boot** orchestrates the application layers.
- **MySQL** stores users, barbers, services, bookings, notifications, reviews, etc.
- **Swagger** provides interactive API documentation.

## Main Technologies
- **Java 21+**
- **Spring Boot** (Web, Data JPA, Validation, DevTools)
- **MySQL**
- **Lombok**
- **Swagger / SpringDoc** (for API documentation)
- **JUnit + Mockito** (for testing)

## Project Structure
The project uses a standard Maven/Spring Boot layout:

![image](https://github.com/user-attachments/assets/d4e2df20-0b1a-4f0f-85a8-935a0341e275)


## Database Model
The system defines six primary entities:
1. **User** – one-to-many with Booking.
2. **Barber** – one-to-many with Booking.
3. **ServiceEntity** – many-to-many with Booking.
4. **Booking** – many-to-one with User, many-to-one with Barber, and many-to-many with ServiceEntity.
5. **Notification** – many-to-one with Booking.
6. **Review** – many-to-one with Booking.
![image](https://github.com/user-attachments/assets/d976b023-8527-4020-94cc-881dbd5bbd78)


## Installation & Setup
1. **Clone the repository** or download the source code.
2. **Create a MySQL database** (e.g., `barbershopdb`).
3. **Configure `application.properties`** in `src/main/resources`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/barbershopdb?useSSL=false&serverTimezone=UTC
   spring.datasource.username=YOUR_USER
   spring.datasource.password=YOUR_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
4. **Build the project** using
   ``` properties
   mvn clean install
   ```
5. **Run the application**
   ``` properties
   mvn spring-boot:run
   ```

6. **Run tests** using
    ``` properties
   mvn clean test
   ```
7. **For more information**, check documentation (PDF) and open the _api_docs.pdf_ to view the endpoints and use Swagger.
   





