# QUIZHUB Use Cases Document

## 1. System Overview

### System Description:
QUIZHUB is a **quiz builder web application** designed to allow users to create and participate in quizzes. The application serves the following purposes:
- **Quiz Creation**: Authenticated users can create quizzes with varying question types (single-answer or multiple-answer).
- **Quiz Participation**: Users can take published quizzes and receive scores based on their answers.
- **Quiz Analytics**: Creators can view the performance of users who took their quizzes, allowing for data gathering on quiz outcomes.

QUIZHUB is built using **Java 17**, **Spring Boot 3**, **Gradle**, **Spring Security**, **Redis Cache**, and **PostgreSQL**, and offers both **RESTful** and **GraphQL** APIs to support integration with web, mobile, or desktop applications. Security is ensured by authentication and data validation.

### Goals:
- Enable users to register, log in, and manage quizzes securely.
- Facilitate quiz creation and management.
- Allow users to participate in quizzes and receive scores.
- Support multiple clients (e.g., mobile apps) for flexibility in quiz creation and participation.

---

## 2. Actors

### Primary Actors:
- **Visitor**: Any user not yet registered or authenticated in the system.
- **Registered User**: A user who has registered and logged in to the QUIZHUB app.
- **Quiz Creator**: A registered user who creates and publishes quizzes for others.
- **Quiz Taker**: A registered user who participates in quizzes created by others.

### Secondary Actors:
- **Admin**: A user with privileges to manage content and access external data (e.g., from Open Trivia API).
- **System**: Backend services responsible for managing authentication, quiz data, user input, and scoring.

---

## 3. Actor Goals

### Visitor:
- Register and create an account to use the platform.
- View the home page and basic content, but cannot interact with quizzes.

### Registered User:
- Authenticate and log in to the system.
- View available published quizzes.
- Take quizzes and view scores for quizzes they participated in.

### Quiz Creator:
- Create quizzes with multiple question types (single-answer or multiple-answer).
- Edit or delete unpublished quizzes.
- Publish quizzes and allow other users to take them.
- View results and scores of participants for quizzes they created.

### Quiz Taker:
- Take published quizzes.
- Submit answers and view total correctness percentage and individual question scores after submission.
- View the list of quizzes they have taken along with their scores.

### Admin:
- Access the system to fetch trivia questions from external APIs (e.g., Open Trivia API).
- Manage the content and quizzes in the system.

---

# QUIZHUB Use Cases

## 1. User Registration and Authentication

- **Actors**: Visitor, Registered User
- **Precondition**: User must have access to the application.
- **Postcondition**: User is authenticated with an active session.

### Main Flow:
1. The visitor accesses the registration page.
2. The visitor provides a valid email and password to create an account.
3. Upon successful registration, the user receives a confirmation email.
4. The registered user logs in with valid credentials.
5. The system authenticates the user and generates an authentication token.

### Alternative Flow:
- If the user provides incorrect credentials, the system prompts an error and denies access.
- If the email is already registered, the system provides feedback and suggests password recovery.

---

## 2. Quiz Creation

- **Actors**: Authenticated User
- **Precondition**: The user must be authenticated.
- **Postcondition**: A new quiz is saved as a draft or published.

### Main Flow:
1. The authenticated user navigates to the "Create Quiz" page.
2. The user provides a title for the quiz.
3. The user adds 1-10 questions to the quiz.
4. Each question is either a single-answer or multiple-correct-answer question.
5. The user specifies possible answers (1-5) and indicates which are correct.
6. The user saves the quiz as a draft.

### Alternative Flow:
- If the quiz is incomplete (e.g., missing a title or questions), the system prompts the user to complete required fields.
- The user can discard a quiz draft at any point.

---

## 3. Quiz Publishing

- **Actors**: Quiz Creator (Authenticated User)
- **Precondition**: A quiz has been saved as a draft.
- **Postcondition**: The quiz becomes available for other users to take.

### Main Flow:
1. The quiz creator navigates to their list of draft quizzes.
2. The user selects a quiz and chooses the "Publish" option.
3. The system marks the quiz as published and available for other authenticated users.

### Alternative Flow:
- Once published, the quiz cannot be edited, but it can be deleted.
- The user may receive a warning about the finality of publishing.

---

## 4. Taking a Quiz

- **Actors**: Authenticated User (Quiz Taker)
- **Precondition**: A quiz must be published.
- **Postcondition**: The quiz taker submits answers and receives a score.

### Main Flow:
1. The quiz taker navigates to the list of available published quizzes.
2. The user selects a quiz to take.
3. The user submits answers to each question.
4. For single-answer questions, only one answer is allowed.
5. For multiple-correct-answer questions, multiple selections are possible.
6. The system calculates the user's score.
7. The user views the total score and individual question scores.

### Alternative Flow:
- If the quiz taker leaves questions unanswered, no points are deducted for those questions.
- Once a quiz is submitted, the quiz taker cannot retake the same quiz.

---

## 5. Viewing Quiz Results

- **Actors**: Quiz Taker, Quiz Creator
- **Precondition**: A quiz has been completed.
- **Postcondition**: Users can view their scores or how others have performed on their quizzes.

### Main Flow (Quiz Taker):
1. The quiz taker submits a completed quiz.
2. The system provides a summary showing:
   - Total correctness percentage.
   - Scores for each individual question.
3. The quiz taker does not see the correct answers, only the score breakdown.

### Main Flow (Quiz Creator):
1. The quiz creator navigates to a published quiz they authored.
2. The creator selects the "View Results" option.
3. The system displays a summary of answers provided by different users without revealing specific answers but showing how well users performed.

---

## 6. Quiz Deletion

- **Actors**: Quiz Creator
- **Precondition**: A quiz must be published.
- **Postcondition**: The quiz is permanently deleted.

### Main Flow:
1. The quiz creator selects a published quiz.
2. The user chooses the "Delete" option.
3. The system permanently removes the quiz from the platform.

### Alternative Flow:
- If a quiz has active responses from users, the creator will receive a prompt confirming deletion.

---

## 7. Fetching Random Questions from Open Trivia API

- **Actors**: Admin User
- **Precondition**: Admin privileges are required.
- **Postcondition**: Random quiz questions are generated and saved.

### Main Flow:
1. The admin user navigates to the "Generate Questions" page.
2. The user triggers the Open Trivia API request.
3. The system retrieves random questions based on content and formats them according to the platform's question standards.
4. The generated questions are stored and made available for quiz creation.

### Alternative Flow:
- The system can automatically generate questions at application startup if configured.

---

## 8. Managing User Solutions

- **Actors**: Authenticated User
- **Precondition**: The user has previously submitted a quiz solution.
- **Postcondition**: The user can view their past solutions.

### Main Flow:
1. The user navigates to their account dashboard.
2. The user selects the "My Solutions" section.
3. The system displays a list of all quizzes the user has taken, along with their scores for each.

### Alternative Flow:
- If no quizzes have been completed, the system informs the user that no solutions are available.

---

## 9. Scoring Logic

- **Actors**: Quiz Taker, System
- **Precondition**: Quiz must have valid questions and answers.
- **Postcondition**: A user’s answers are scored based on correctness.

### Main Flow:
1. For single-answer questions, the system assigns:
   - +1 point for correct answers.
   - -1 point for incorrect answers.
   - 0 points for skipped questions.
2. For multiple-answer questions, the system calculates:
   - Positive weights for correct answers (e.g., +⅓ for each correct option).
   - Negative weights for incorrect answers (e.g., -½ for each wrong option).
3. The total score for the question is based on the sum of these weights.

### Alternative Flow:
- If a user selects all possible answers, the score can result in zero or negative points.

---

## 10. Additional Considerations

### 10.1. Trivia API Integration
- Admin users can trigger the system to fetch questions from the **Open Trivia API**.
- The system fetches and stores trivia questions, making them available for users to include in their quizzes.
- The API integration can be run as a background job or manually triggered by Admins.

### 10.2. Security Considerations
- All quiz-related operations (creation, participation, etc.) are restricted to authenticated users.
- Strong validation is in place to prevent enumeration attacks (e.g., users guessing quiz URLs to gain unauthorized access).
- User input is sanitized to prevent security vulnerabilities such as SQL injection or XSS.

---


## Conclusion
The QUIZHUB app provides a comprehensive API for quiz creation, user participation, and scoring. This use case document outlines the key user interactions, system behaviors, and core functionality needed for successful operation.

