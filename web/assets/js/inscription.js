/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const servletUrl = "InscriptionServlet";
const redirectionUrl = "index.html";

function submitForm(event) {
    event.preventDefault(); 
    
    let pseudonyme = document.getElementById('pseudonyme').value;
    let email = document.getElementById('email').value;
    let motDePasse = document.getElementById("motDePasse").value;
    let dateNaissance = document.getElementById("naiss").value;
    let sexe = document.getElementById("sexe").value;
    let ville = document.getElementById("ville").value;
    
    let infosJoueur = {pseudonyme, email, motDePasse, dateNaissance, sexe, ville};
    
    let urlEncodedDataPairs = [], name;
    for(name in infosJoueur) {
        urlEncodedDataPairs.push(`${name}=${infosJoueur[name]}`);
    }
    
    console.log("Envoi de la demande d'inscription");
    requete = new XMLHttpRequest();
    requete.onreadystatechange = majPage;
    requete.open("POST", servletUrl);
    requete.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    requete.send(urlEncodedDataPairs.join('&'));
}

function majPage () {
    if (requete.readyState === 4) {
        console.log(requete.responseText);
        if (requete.status === 200) {
            iziToast.success({
            title: 'Inscription terminée !',
            message: 'Vous allez être redirigé.',
            position: 'center',
            transitionIn: 'fadeIn',
            onClosing: function(instance, toast, closedBy){
                window.location.href = redirectionUrl;
                }
            });
        } else {
            iziToast.error({
            title: 'Oops !',
            message: 'Une erreur est survenue.',
            position: 'center',
            transitionIn: 'fadeIn'
            });
        }
    } 
}

const form = document.getElementById("form");
form.addEventListener("submit", submitForm, true);