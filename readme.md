# Web Quiz Engine

This project is a multi-user web service for creating and solving quizzes, built as a project for **JetBrains Academy (Hyperskill)**.

It provides a REST API for user registration, quiz creation, and quiz solving. It uses **Spring Boot, Spring Security** for authentication, and **Spring Data JPA** for database persistence.

Application source code is located within the `Web Quiz Engine with Java/task/` directory.

## Technologies Used

* **Spring Boot**
    * Spring Web
    * Spring Data JPA 
    * Spring Security
    * Spring Boot Validation
* **H2 Database**
* **Gradle**

# API Endpoints

#### `POST /api/register`

* **Description:** Registers a new user.
  * **Request Body:**
      ```json
      {
      "email": "test@mail.org",
      "password": "strongpassword"
      }
      ```
* **Responses:**
    * `200 OK`: Successful registration.
    * `400 Bad Request`: If the email format is invalid, the password is less than 5 characters, or the email is already taken.

---

### Quiz Management

*(All endpoints below require HTTP Basic Authentication)*

#### `POST /api/quizzes`

* **Description:** Creates a new quiz.
* **Request Body:**
    ```json
    {
      "title": "The Java Logo",
      "text": "What is depicted on the Java logo?",
      "options": ["Robot", "Tea leaf", "Cup of coffee", "Bug"],
      "answer": [2]
    }
    ```
  *Note: `answer` can be empty `[]` or contain multiple indices `[0, 1]`.*
* **Response:**
    * `200 OK`: Returns the created quiz object.
    * `400 Bad Request`: If validation fails.

#### `GET /api/quizzes/{id}`

* **Description:** Retrieves a single quiz by ID.
* **Response:**
    * `200 OK`: Returns the quiz object (without the `answer` field).
    * `404 Not Found`: If the quiz with that ID doesn't exist.

#### `GET /api/quizzes`

* **Description:** Retrieves all quizzes with pagination (10 quizzes per page).
* **Query Parameters:**
    * `page` (int, default=0): The page number.
* **Response:**
    * `200 OK`: Returns a `Page` object containing the list of quizzes.

#### `POST /api/quizzes/{id}/solve`

* **Description:** Submits an answer for a specific quiz.
* **Request Body:**
    ```json
    {
      "answer": [0, 1]
    }
    ```
* **Response:**
    * `200 OK`: Returns a feedback object.
    * `404 Not Found`: If the quiz doesn't exist.

#### `GET /api/quizzes/completed`

* **Description:** Gets the current user's completed quizzes, sorted by completion date (most recent first).
* **Query Parameters:**
    * `page` (int, default=0): The page number to retrieve.
* **Response:**
    * `200 OK`: Returns a `Page` object containing completion data (`id` and `completedAt`).

#### `DELETE /api/quizzes/{id}`

* **Description:** Deletes a quiz by ID.
* **Response:**
    * `204 No Content`: If the quiz was successfully deleted.
    * `404 Not Found`: If the quiz doesn't exist.

## Testing

This project comes with a test suite provided by **Hyperskill**. The tests cover both **unit-level logic** and full **integration tests**.
