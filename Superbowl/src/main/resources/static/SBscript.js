window.onload = function() {
  let hamburgerButton = document.querySelector("#hamburger");
  var pageNavigation = document.querySelector("#NavBar");
  hamburgerButton.onclick = function() {
    pageNavigation.classList.toggle("open");
  };
};
