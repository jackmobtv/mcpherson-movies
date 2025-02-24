var wsProtocol = (window.location.protocol === 'https:' ? 'wss://' : 'ws://');
const wsUri = wsProtocol + window.location.host + document.location.pathname + "/checkEmail"; // Adjust context path as necessary.

let socket;

$(document).ready(function() {
    socket = new WebSocket(wsUri);

    socket.onopen = function() {
        console.log("WebSocket is connected."); // Confirm connection
    };

    socket.onmessage = function(event) {
        const emailExists = event.data === "true"; // Check for "true" response
        const emailInput = $("#email");
        const emailError = $("#emailError");
        console.log(emailExists);

        if (emailExists) {
            emailInput.addClass("is-invalid");
            emailError.text("A user with that email already exists. Please login or reset your password.");
        } else {
            emailInput.removeClass("is-invalid");
            emailError.text("");
        }
    };

    socket.onerror = function(error) {
        console.error("WebSocket Error: ", error);
    };

    socket.onclose = function() {
        console.log("WebSocket connection closed.");
    };
});

function checkEmail(event) {
    const email = event.target.value;
    if (email) {
        socket.send(email); // Send the email to the server
        console.log(`Sent email for check: ${email}`); // Debug log
    }
}