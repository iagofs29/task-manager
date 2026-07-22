# Task Manager

A simple command-line Task Manager developed in **Java** using **Maven**. The application allows users to create, update, delete and list tasks while persisting all data in a JSON file using Google's **Gson** library.

This project was developed as a learning exercise to practice object-oriented programming, layered architecture, exception handling, file persistence and Java collections.

---

## Features

- Create tasks
- Modify existing tasks
- Delete tasks
- Change task status
- List all tasks
- List tasks by status:
  - To Do
  - In Progress
  - Done
- Persistent storage using JSON
- Automatic loading of saved tasks on application startup
- Custom or default data file location

---

## Technologies

- Java 17 or later
- Maven
- Gson
- Java NIO (`Path`, `Files`)
- JSON

---

## Project Structure

```
src
└── main
    ├── java
    │   └── taskmanager
    │       ├── controllers
    │       ├── models
    │       ├── repository
    │       ├── services
    │       ├── console
    │       └── Main.java
    └── resources
```

### Responsibilities

| Package | Responsibility |
|----------|----------------|
| **controllers** | User interaction and application flow |
| **console** | Console input validation and utility methods |
| **services** | Business logic |
| **repository** | JSON persistence |
| **models** | Domain entities |

---

## Architecture

The application follows a layered architecture:

```
Console
      │
      ▼
Controllers
      │
      ▼
TaskService
      │
      ▼
TaskRepository
      │
      ▼
tasks.json
```

Each layer has a single responsibility:

- Controllers handle user interaction.
- Services contain business logic.
- Repository manages persistence.
- Models represent domain objects.

---

## Task Model

Each task contains:

- ID
- Title
- Description
- Status

Available statuses:

- TO DO
- IN PROGRESS
- DONE

---

## Persistence

Tasks are stored in a JSON file using **Gson**.

Example:

```json
[
  {
    "id": 1,
    "title": "Study Java",
    "description": "Finish Task Manager project",
    "status": "IN_PROGRESS"
  }
]
```

---

## Error Handling

The application validates:

- Invalid numeric input
- Empty text fields
- Invalid task IDs
- Invalid file names
- JSON serialization errors
- File system errors

Exceptions are handled at the appropriate layer to keep responsibilities separated.

---

## Design Decisions

Some implementation decisions taken during development:

- Layered architecture.
- Dependency injection through constructors.
- Repository pattern for persistence.
- In-memory task management using `List<Task>`.
- Sequential task IDs.
- JSON persistence through Gson.
- Use of Java NIO (`Path` and `Files`) instead of legacy `File`.

---

## Running the Project

Clone the repository:

```bash
git clone https://github.com/iagofs29/task-manager.git
```

Move into the project:

```bash
cd task-manager
```

Compile:

```bash
mvn clean compile
```

Run:

```bash
mvn exec:java
```

---

## Future Improvements

- Command-line arguments (CLI)
- Search tasks by keyword
- Sort tasks
- Due dates
- Priority levels
- Unit tests (JUnit)
- Logging
- CSV export
- Multiple persistence formats
- Spring Boot REST API

---

## Learning Objectives

This project was built to practice:

- Object-Oriented Programming
- Java Collections
- Exception Handling
- File I/O
- JSON serialization/deserialization
- Layered architecture
- Separation of concerns
- Maven project management

---

## Author

**Iago Fanjul Sánchez**
Software Engineering Student
