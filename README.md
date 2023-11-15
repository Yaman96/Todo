
# Todo List Application

This is a simple Todo List web application built using Java and Spring Boot. It utilizes MySQL for data storage, allowing users to create, edit, and delete tasks. The task list is displayed with pagination for better organization.
![Todo List screenshot](https://github.com/Yaman96/Todo/blob/0ec725bb1f8d002d2a61dd12dd0cb60e8b9bbc2e/screenshots/2023-11-16_01-25-27.png)

## Features

- **Create Tasks**: Easily add new tasks to your todo list.
- **Set Deadlines**: Specify deadlines for tasks to better manage your schedule.
- **Edit Tasks**: Update task details as needed.
- **Delete Tasks**: Remove tasks that are completed or no longer relevant.
- **Pagination**: The task list is paginated for a more organized view.

## Technologies Used

- **Backend**: Java, Spring Boot, Spring Data JPA (using JpaRepository)
- **Database**: MySQL
- **Frontend**: Bootstrap, JavaScript

## How to Run

1. Clone the repository.
2. Configure your MySQL database settings in `application.properties`.
3. Build and run the application using your preferred IDE or using the following command:

   ```bash
   ./mvnw spring-boot:run
