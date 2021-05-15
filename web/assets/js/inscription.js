/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const servletUrl = "inscriptionServlet";

function submitForm(event) {
    event.preventDefault(); 
    console.log("Envoi de l'inscription");
    
    let pseudonyme = document.getElementById('pseudonyme').value;
    let motDePasse = document.getElementById("motDePasse").value;
    let age = document.getElementById("age").value;
    let sexe = document.getElementById("sexe").value;
    let ville = document.getElementById("ville").value;
    
    console.log({pseudonyme, motDePasse, age, sexe, ville});

    /*
    requete = new XMLHttpRequest();
    requete.open("POST", servletUrl);
    requete.onreadystatechange = majPage;
    requete.send(null);*/
}

function majPage () {
    if (requete.readyState === 4) {
        if (requete.status === 200) {
            console.log("Inscription prise en compte");
        } 
        else 
        {
            console.log("Erreur requête : " + requete.status);
        }
    } 
}

const form = document.getElementById("form");
form.addEventListener("submit", submitForm, true);