var newButtonExists = false;

function reserve(){
    var password = document.getElementById("password").value;
    var amount = document.getElementById("amountPersons").value;
    var mail = document.getElementById("mail").value;
    var restaurantId = Number(document.getElementById("restaurant").value);

    var reservation = {
        "password" : password,
        "amountPeople" : amount,
        "mail" : mail,
        "restaurantId" : restaurantId
    }
    var json = JSON.stringify(reservation);

    var xhttp = new XMLHttpRequest();
    xhttp.open("POST","http://localhost:8080/reservationsPost/",false);
    xhttp.setRequestHeader("Content-Type","application/json");
    xhttp.send(json);

    getReservation();

}

function getReservation(){
    var list = document.getElementById("list");
    
    while (list.hasChildNodes()) {
        list.removeChild(list.firstChild);
     }

    var request = new XMLHttpRequest();
    var reservations;
    request.open("GET","http://localhost:8080/reservationsGet/",false);
    request.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            reservations = JSON.parse(this.responseText);
            for(var i = 0; i < reservations.length; i++){
                var x = document.createElement("LI");
                var t = document.createTextNode(reservations[i].amountPeople+" Plätze: "+reservations[i].restaurantName+" Res.Nr.:"+reservations[i].id);
                x.appendChild(t);
                document.getElementById("list").appendChild(x);
            }
        } 
    }
    request.send();

}
function showReservations(){
    var password = document.getElementById("password").value;
    var mail = document.getElementById("mail").value;

    if(mail == "" | password == 0){
        alert("Bitte geben Sie Passwort und Mail - Adresse an.")
    }
    else{
        var reservationToLogin = {
            "password" : password,
            "mail" : mail,
        }
        var json3 = JSON.stringify(reservationToLogin);
    
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST","http://localhost:8080/login/",false);
        xhttp.setRequestHeader("Content-Type","application/json");
        xhttp.send(json3);

        getReservation();
    }
   
}
function cancel(){
    
    var reservationNumber = document.getElementById("number").value;
    var mail = document.getElementById("mail").value;
    var password = document.getElementById("password").value;
    var reservationToCancel = {
        "id" : reservationNumber,
        "password" : password,
        "mail" : mail
    }
    if(mail == "" | password == 0 | reservationNumber == 0){
        alert("Bitte geben Sie Passwort und Mail - Adresse an.")
    }
    else{
    var json1 = JSON.stringify(reservationToCancel);

    var xhttp = new XMLHttpRequest();
    xhttp.open("POST","http://localhost:8080/cancellation/",false);
    xhttp.setRequestHeader("Content-Type","application/json");
    xhttp.send(json1);
    }
    getReservation();

}
function change(){
    var list = document.getElementById("list");
    while (list.hasChildNodes()) {
        list.removeChild(list.firstChild);
     }
    var reservationNumber = document.getElementById("number").value;
    var mail = document.getElementById("mail").value;
    var password = document.getElementById("password").value;
   
    if(mail == "" | password == 0 | reservationNumber == 0){
        alert("Bitte geben Sie Passwort und Mail - Adresse an.")
    }
    else{
        var request = new XMLHttpRequest();
        request.open("GET","http://localhost:8080/reservations/"+reservationNumber+"",false);
        request.onreadystatechange = function(){
            if(this.readyState == 4 && this.status == 200){
                var res = JSON.parse(this.responseText);
                var x = document.createElement("LI");
                var t = document.createTextNode(res.amountPeople+" Plätze: "+res.restaurantName+" Res.Nr.:"+res.id);
                x.appendChild(t);
                document.getElementById("list").appendChild(x);
            } 
        }
        request.send();
        if(!newButtonExists){
            var newPlace = document.getElementById("new");
            var newInput = document.createElement("input");
            newInput.setAttribute("type","number");
            newInput.setAttribute("min","1");
            newInput.setAttribute("id","changeNumber");
            var newLabel = document.createElement("label");
            newLabel.setAttribute("for","changeNumber");
            newLabel.innerHTML = "Neue Anzahl Personen:"
            var newButton = document.createElement("button");
            newButton.innerHTML = "bestätigen";
            newButton.onclick = function() {newButtonClick()};
            newPlace.appendChild(newLabel);
            newPlace.appendChild(newInput);
            newPlace.appendChild(newButton);
        }
         
        
    }
    function newButtonClick(){
        var reservationNumber = document.getElementById("number").value;
        var amountPeople = document.getElementById("changeNumber").value;

        var reservationToChange = {
            "amountPeople" : amountPeople,
            "id" : reservationNumber
        }
        var json2 = JSON.stringify(reservationToChange);

        var xhttp = new XMLHttpRequest();
        xhttp.open("POST","http://localhost:8080/change/",false);
        xhttp.setRequestHeader("Content-Type","application/json");
        xhttp.send(json2);

        var list = document.getElementById("list");
        while (list.hasChildNodes()) {
        list.removeChild(list.firstChild);
        }

        var request = new XMLHttpRequest();
        request.open("GET","http://localhost:8080/reservations/"+reservationNumber+"",false);
        request.onreadystatechange = function(){
            if(this.readyState == 4 && this.status == 200){
                var res = JSON.parse(this.responseText);
                var x = document.createElement("LI");
                var t = document.createTextNode(res.amountPeople+" Plätze: "+res.restaurantName+" Res.Nr.:"+res.id);
                x.appendChild(t);
                document.getElementById("list").appendChild(x);
            } 
        }
        request.send();

        newButtonExists = true;
        return newButtonExists;
     
        
    }

}