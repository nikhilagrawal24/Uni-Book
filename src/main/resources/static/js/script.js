// login.html page use 
$(document).ready(function () {
    // responsive 600px to 300px
    $("#signupbtnn").click(function () {
        $("#login").css({
            "display":"none"
        })
        $("#ragister").css({
            "display":"flex"
        })
    })
    $("#loginbtnn").click(function () {
        $("#ragister").css({
            "display":"none"
        })
        $("#login").css({
            "display":"flex"
        })
    })
});

// hamburger icon
$(document).ready(function () {
    console.log("ready");
    $("#hamburger").click(function () {
        console.log("clicked");
        $(".hambody").css({
            "display": "block"
        })
    })
    $(".hamclose").click(function () {
        $(".hambody").css({
            "display": "none"
        })
    })
});

var acc = document.getElementsByClassName("ContactAccordian");
var i;
for (i = 0; i < acc.length; i++) {
  acc[i].addEventListener("click", function () {
    this.classList.toggle("active");
    var cardBox = this.nextElementSibling;
    if (cardBox.style.display === "flex") {
      cardBox.style.display = "none";
    } else {
      cardBox.style.display = "flex";
    }
  });
}

//arrow dropdown in navbar
let arrow = document.getElementById("arrow");
let dropdown = document.getElementById("dropdown");
arrow.addEventListener("click", function () {
  dropdown.classList.toggle('hide')
  dropdown.classList.toggle('show')
})

// contact form
let addIcon = document.getElementById("addIcon");
let contactDetails = document.getElementById("contactDetails");
let closeBtn = document.getElementById("closeBtn");

addIcon.onclick = function () {
  contactDetails.style.display = "block";
};
closeBtn.onclick = function () {
  contactDetails.style.display = "none";
};
