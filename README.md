# Exam-Generator

A Java-based console application for managing database-backed exams. Supports various question types (multiple-choice, open-ended), answers, and exam management through a menu-driven interface.

---

## Features

- **Menu-driven Exam Management**  
  Provides a console menu to:
  - Add and manage questions (multiple-choice and open-ended).
  - Add and manage answers for questions.
  - Administer exams and record responses.
  - Persist data in a database file.

- **Question Types**  
  - **MultiChoiceQuestion**: Supports adding multiple-choice options and correct answers.
  - **OpenQuestion**: Supports adding free-text exam questions.

- **Answer Handling**  
  - **MultiChoiceAnswer**: Accepts user’s choice for multiple-choice questions.
  - **Answer**: Records student’s answer for open-ended questions.

- **Persistence**  
  - Uses a SQLite database (`db_File.backup`) located in `Test_Files/` to store questions and answers.
  - Precompiled classes handle database connections and CRUD operations.

---

## Repository Structure

```
Exam-Generator-main/
├── Test_Files/
│   └── db_File.backup
├── bin/
│   └── Java_Project_For_DB/
│       ├── Answer.class
│       ├── Manager.class
│       ├── MenuCases.class
│       ├── MultiChoiceAnswer.class
│       ├── MultiChoiceQuestion.class
│       ├── OpenQuestion.class
│       ├── Program.class
│       └── Test.class
└── src/
    └── Java_Project_For_DB/
        ├── Answer.java
        ├── Manager.java
        ├── MenuCases.java
        ├── MultiChoiceAnswer.java
        ├── MultiChoiceQuestion.java
        ├── OpenQuestion.java
        ├── Program.java
        ├── Question.java
        ├── QuestionTypes.java
        ├── Set.java
        ├── Sort.java
        └── Test.java
```

- **Test_Files/db_File.backup**  
  SQLite database file with initial schema and test data.

- **bin/Java_Project_For_DB/**  
  Compiled bytecode for all Java classes.  
  Contains `.class` files for runtime without recompilation.

- **src/Java_Project_For_DB/**  
  Java source code files:
  - `Answer.java`, `MultiChoiceAnswer.java`: Define answer handling.
  - `MultiChoiceQuestion.java`, `OpenQuestion.java`: Define question types.
  - `Manager.java`: Handles database interactions (CRUD) via JDBC.
  - `MenuCases.java`: Defines menu options and logic flow.
  - `Program.java`: Main class; launches the console menu interface.
  - `Question.java`, `QuestionTypes.java`, `Set.java`, `Sort.java`: Utilities for question organization and sorting.
  - `Test.java`: Contains test methods for validating functionality.

---

## Prerequisites

- **Java JDK** version 8 or higher.
- **SQLite JDBC** driver (bundled with the project).
- **JDBC-compatible** environment.

---

## Compilation & Execution

### Using Precompiled Classes

1. Ensure the SQLite database file is present: `Test_Files/db_File.backup`.
2. From the project root, run:
   ```bash
   cd Exam-Generator-main/bin/Java_Project_For_DB
   java -classpath ".:sqlite-jdbc.jar" Program
   ```
   Adjust classpath separator (`:` for Linux/macOS, `;` for Windows).

### Compiling from Source

1. Open a terminal in the project root directory.
2. Compile all `.java` files:
   ```bash
   javac -cp ".:sqlite-jdbc.jar" -d bin/Java_Project_For_DB src/Java_Project_For_DB/*.java
   ```
3. Run the program:
   ```bash
   cd bin/Java_Project_For_DB
   java -cp ".:sqlite-jdbc.jar" Program
   ```

---

## Usage

1. **Launch Program**  
   Execute `Program.class` as shown above. The main menu appears:
   ```
   Welcome to Exam Generator
   1. Add Question
   2. Add Answer
   3. Administer Exam
   4. Exit
   Enter choice:
   ```
2. **Manage Questions**  
   - Select “Add Question” to create a multiple-choice or open question.
   - Follow prompts to input question text, choices (if applicable), and correct answer.

3. **Manage Answers**  
   - Select “Add Answer” to record an answer for an existing question.
   - Follow prompts to enter student ID, question ID, and answer text or choice.

4. **Administer Exam**  
   - Choose “Administer Exam” to present questions to a student sequentially.
   - Collect and store responses in the database.

5. **Exit**  
   - Selecting “Exit” closes the program and commits any pending changes to the database.

---

## Database Schema

The SQLite database (`db_File.backup`) contains two primary tables:

- **Questions**  
  - `id` (INTEGER PRIMARY KEY AUTOINCREMENT)  
  - `type` (TEXT) – `"MULTI"` or `"OPEN"`  
  - `text` (TEXT) – Question prompt  
  - `options` (TEXT) – Comma-separated options for multiple-choice (NULL for open questions)  
  - `correct_answer` (TEXT) – Correct answer string or choice

- **Answers**  
  - `id` (INTEGER PRIMARY KEY AUTOINCREMENT)  
  - `question_id` (INTEGER) – Foreign key to `Questions.id`  
  - `student_id` (TEXT) – Unique student identifier  
  - `answer_text` (TEXT) – Student’s answer (choice or free text)  
  - `is_correct` (INTEGER) – `0` (false) or `1` (true), computed upon insertion

---

## Acknowledgements

- Project originally developed for coursework on database-driven Java applications.
- Uses SQLite for lightweight persistence and JDBC for database connectivity.
- Inspired by standard exam administration systems.

---

*Enjoy using the exam-generator project!*
