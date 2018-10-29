loadUser();
// Felhasznalo adatainak lekerese
function loadUser() {
  fetch('/user')
    .then(function retriveUserData(response) {
      // A response valtozo tartalmazza a felhasznalo adatait
      return response.json();
    })
  // Oldal feltoltese a felhasznalo adataival
    .then(function greetUser(userData) {
      // az userData json!
      if (userData.userRole != null) {
        loadCusomHeader(userData);
      } else {
        loadVisitorHeader();
      }
    });
}

function loadVisitorHeader() {
    document.getElementById("welcome-p").innerHTML = "Welcome Visitor!";
}

function loadCusomHeader(userData) {
    document.getElementById("welcome-p").innerHTML = "Welcome " + userData.givenName + " " + userData.familyName + "!";
}