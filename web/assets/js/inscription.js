/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function submitForm(event) {
    event.preventDefault(); 
    console.log("Envoi de l'inscription");
    
    let pseudonyme = document.getElementById("pseudonyme").value();
    let motDePasse = document.getElementById("motDePasse").value();
    let age = document.getElementById("age").value();
    let sexe = document.getElementById("sexe").value();
    let ville = document.getElementById("ville").value();
    
    console.log({pseudonyme, motDePasse, age, sexe, ville});
}

const form = document.getElementById("form");
form.addEventListener("submit", submitForm, true);