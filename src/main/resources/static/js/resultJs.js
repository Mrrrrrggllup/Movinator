let movie1 = document.getElementById("movie1");
let movie2 = document.getElementById("movie2");
let movie3 = document.getElementById("movie3");

let button1 = document.getElementById("button1");
let button2 = document.getElementById("button2");
let button3 = document.getElementById("button3");

button1.addEventListener("click",function () {

    movie2.style.display ="none";
    movie3.style.display ="none";
    movie1.style.display = "flex";
});

button2.addEventListener("click",function () {

    movie1.style.display ="none";
    movie3.style.display ="none";
    movie2.style.display = "flex";
});

button3.addEventListener("click",function () {

    movie2.style.display ="none";
    movie1.style.display ="none";
    movie3.style.display = "flex";
});
