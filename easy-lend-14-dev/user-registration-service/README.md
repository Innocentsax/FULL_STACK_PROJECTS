# User Registration Service (Backend)
Handles the user registration process including email verification, security(jwt) and forgot password for a fintech service that provides a peer-to-peer (P2P) lending platform for its users.

## Base Method
**registerUser()**:

## Responsibilities
The User Registration Service is responsible for the following tasks:

- Collecting user's personal information such as email, full name, password and any other relevant information.
- Validating user input and ensuring compliance with privacy regulations.
- Securely storing the user information in the database.
- Sending verification email to registered user.
- Sending forgot password link via email.

Application Github Link: https://github.com/decadevs/easy-lend-14.git

## Technologies Used
1. Java 17
2. Maven
3. Spring Boot
4. Docker
5. MySQL
6. RabbitMQ

## Setting up the Application on your local machine
1. Install JDK 17 and above and MySQL workbench in your system.
2. Inside MySQL workbench, create a schema named easylend
3. Run the application as Spring Boot Project.
4. Application url: http://localhost:8082
