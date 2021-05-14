/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function submitForm(event) {
    event.preventDefault(); 
    console.log("Envoi de l'inscription");
    
    
    
}

const form = document.getElementById("form");
form.addEventListener("submit", submitForm, true);