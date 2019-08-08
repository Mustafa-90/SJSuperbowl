window.onload = function() {
  let hamburgerButton = document.querySelector("#hamburger");
  var pageNavigation = document.querySelector("#PageNavigation");
  hamburgerButton.onclick = function() {
    pageNavigation.classList.toggle("open");
  };
};
