let connectionForm = document.getElementById("login");
let inscriptionForm = document.getElementById("sign-up");
let inscriptionButton = document.getElementById("register-link");

inscriptionButton.addEventListener("click", function () {


    connectionForm.style.display = "none";
    inscriptionForm.style.display = "block";
});

document.getElementById("connection-link").addEventListener("click", function () {

    inscriptionForm.style.display = "none";
    connectionForm.style.display = "block";
});