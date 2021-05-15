/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const servletUrl = "ConnectionServlet";
const redirectionUrl = "gameMenu.jsp?pseudo=";

function submitForm(event) {
    event.preventDefault(); 
    
    let pseudonyme = document.getElementById('pseudonyme').value;
    let motDePasse = document.getElementById("motDePasse").value;
    
    let infosJoueur = {pseudonyme, motDePasse};
    
    let urlEncodedDataPairs = [], name;
    for(name in infosJoueur) {
        urlEncodedDataPairs.push(`${name}=${infosJoueur[name]}`);
    }
    
    console.log("Envoi de la demande de connexion");
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
            title: 'Connexion réussie !',
            message: 'Vous allez être redirigé.',
            position: 'center',
            transitionIn: 'fadeIn',
            timeout: 1000,
            pauseOnHover: false,
            onClosed: function(instance, toast, closedBy){
                window.location.href = redirectionUrl + document.getElementById('pseudonyme').value;
                }
            });
        } else {
            iziToast.error({
            title: 'Oops !',
            message: 'Vos identifiants sont incorrects.',
            position: 'center',
            transitionIn: 'fadeIn',
            pauseOnHover: false
            });
        }
    } 
}

const form = document.getElementById("form");
form.addEventListener("submit", submitForm, true);