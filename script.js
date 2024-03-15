// script.js

function submitForm(event) {
    event.preventDefault(); // Prevent default form submission

    // Retrieve form data
    var name = document.getElementById('name').value;
    var email = document.getElementById('email').value;
    var age = document.getElementById('age').value;
    var dob = document.getElementById('dob').value;

    // Simple client-side validation
    if (name.trim() === "" || email.trim() === "" || age.trim() === "" || dob.trim() === "") {
        alert("All fields are required!");
        return;
    }

    if (isNaN(age) || parseInt(age) <= 0) {
        alert("Age must be a positive number!");
        return;
    }

    // Send data to the backend
    sendDataToBackend(name, email, age, dob);
}

function sendDataToBackend(name, email, age, dob) {
    // Assuming using AJAX to send data to backend
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8000/submitFormData", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
            } else {
                alert("Failed to submit form data.");
            }
        }
    };
    var data = JSON.stringify({ "name": name, "email": email, "age": age, "dob": dob });
    xhr.send(data);
}
