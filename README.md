# BASIC WEB APPLICATION

- This is a simple web application for submitting user data through a form. It includes both frontend and backend components to handle form submission, data processing, and storage in a MySQL database.

## Technologies Used

### Frontend:
- HTML
- CSS
- JavaScript

### Backend:
- Java
- MySQL

## Setup Instructions

### Database Setup:
- Ensure you have MySQL installed and running.
- Create a new database named `hello` (user-defined).
- Ensure that JDBC driver for MySQL is added to the classpath.
- Execute the following SQL query to create the necessary table:

```sql
CREATE TABLE user_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    dob DATE NOT NULL
);

);

## Backend Setup:
- Open the FormDataSubmission.java file.
- Update the JDBC_URL, JDBC_USER, and JDBC_PASSWORD constants with your MySQL database credentials.


## Frontend Setup:
- No additional setup required for frontend as it's static HTML, CSS, and JavaScript.

## Execution
- Compile and run the Java file.
- This will start the backend server at http://localhost:8000.

## Access the Application:
- Open your web browser and navigate to http://localhost:8000 by opening index.html file.
- Fill in the form with your details and submit.

## File Structure
- index.html: Contains the HTML structure of the form submission page.
- style.css: Stylesheet for the HTML page.
- script.js: JavaScript file handling form submission request to the backend.
- FormDataSubmission.java: Backend Java file handling HTTP requests, form data processing, and database interaction.

## Git Repository
- [Repository Link] (https://github.com/Shivasunny555/basic_web_application)

## Contributors
- BUSHPAKA SHIVA

## License
-This project is licensed under the MIT License