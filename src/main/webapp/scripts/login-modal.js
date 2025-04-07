$(document).ready(function (){
    const emailInputM = document.getElementById("emailM");
    const rememberMeCheckboxM = document.getElementById("rememberMeM");
    const loginFormM= document.getElementById("modalLoginForm");

    const savedEmailM = localStorage.getItem("rememberedEmail");
    if (savedEmailM) {
        emailInputM.value = savedEmailM;
        rememberMeCheckboxM.checked = true;
    }

    loginFormM.addEventListener("submit", function () {
        if (rememberMeCheckboxM.checked) {
            localStorage.setItem("rememberedEmail", emailInputM.value);
        } else {
            localStorage.removeItem("rememberedEmail");
        }
    });
});