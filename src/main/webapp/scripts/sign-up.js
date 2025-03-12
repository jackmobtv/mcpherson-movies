let wsProtocol = (window.location.protocol === 'https:' ? 'wss://' : 'ws://');
const wsUri = wsProtocol + window.location.host + document.location.pathname + "/checkEmail";

let socket;
let HiddenEmailError = "";
let emailInput;
let emailError;

$(document).ready(function() {
    socket = new WebSocket(wsUri);
    HiddenEmailError = $("#emailErrorJS").html();
    emailInput = $("#email");
    emailError = $("#emailError");

    // if(email !== ""){
    //     emailInput.addClass("is-invalid");
    //     emailError.text("A user with that email already exists. Please login or reset your password.");
    // }

    socket.onopen = function() {
        console.log("WebSocket is connected."); // Confirm connection
    };

    socket.onmessage = function(event) {
        const emailExists = event.data === "true";

        console.log(emailExists);
        console.log(HiddenEmailError);
        if (emailExists) {
            emailInput.addClass("is-invalid");
            emailError.text("A user with that email already exists. Please login or reset your password.");
        } else {
            if(HiddenEmailError === ""){
                emailInput.removeClass("is-invalid");
                emailError.text("");
            } else {
                emailError.text(HiddenEmailError);
            }
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
    if(email) {
        socket.send(email); // Send the email to the server
        console.log("Sent email for check: ${email}"); // Debug log
    } else {
        emailError.text("Please Enter An Email");
        emailInput.addClass("is-invalid");
    }
}