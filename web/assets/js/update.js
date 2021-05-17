/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
console.log('oui')
const servletUrl = 'UpdateServlet';

function submitForm(event) {
    event.preventDefault(); 
    console.log('Envoi des informations');
    
    let motDePasse = document.getElementById('motDePasse').value;
    let naiss = document.getElementById('naiss').value;
    let sexe = document.getElementById('sexe').value;
    let ville = document.getElementById('ville').value;
    let mail = document.getElementById('mail').value;
    
    let data = {
        motDePasse,
        naiss,
        sexe,
        ville,
        mail
    };

    requete = new XMLHttpRequest();
    requete.onreadystatechange = majPage;
    requete.open("POST", servletUrl);
    requete.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    let urlEncodedDataPairs = [], name;
    for(name in data) {
        urlEncodedDataPairs.push(`${name}=${data[name]}`);
    }
    
    requete.send(urlEncodedDataPairs.join('&'));
}

function majPage() {
    if (requete.readyState === 4) {
        if (requete.status === 200) {
            console.log('Inscription prise en compte');
            document.querySelector('#errorBox').style.display = 'none';
            iziToast.success({
                title: 'Success',
                message: 'Utilisateur modifié avec succès !',
                position: 'bottomRight',
                transitionIn: 'bounceInLeft',
                onClosed: function(instance, toast, closedBy){
                    window.history.back();
                }
            });
        } 
        else 
        {
            let response = JSON.parse(this.responseText);
            console.log(`Erreur requête : ${requete.status}`);
            const errorBox = document.querySelector('#errorBox');
            errorBox.innerHTML = 'Modification échoué !';
            errorBox.style.display = 'block';
        }
    } 
}

const form = document.getElementById("form");
form.addEventListener("submit", submitForm, true);